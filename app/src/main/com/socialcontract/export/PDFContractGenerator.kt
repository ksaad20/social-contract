```kotlin
package com.socialcontract.android.contract

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Generates a human-readable PDF representation of a cultivation contract.
 *
 * The generator intentionally performs presentation only. Contract calculations,
 * settlement logic, and persistence remain outside this class.
 */
class PdfContractGenerator(
    private val context: Context
) {

    data class ContractPdfData(
        val contractNumber: String,
        val createdAt: Date = Date(),
        val startDate: Date? = null,
        val endDate: Date? = null,

        val landDescription: String,
        val landArea: Double,
        val landAreaUnit: String,

        val cropName: String,
        val cultivationDurationDays: Int? = null,

        val parties: List<Party>,
        val costs: List<CostItem> = emptyList(),

        val expectedYield: Double? = null,
        val yieldUnit: String? = null,
        val expectedRevenue: Double? = null,

        val farmerSharePercent: Double? = null,
        val landownerSharePercent: Double? = null,

        val notes: String? = null
    )

    data class Party(
        val name: String,
        val role: String,
        val phone: String? = null,
        val address: String? = null
    )

    data class CostItem(
        val category: String,
        val description: String? = null,
        val amount: Double,
        val currency: String = "BDT"
    )

    /**
     * Generates the PDF and returns the resulting file.
     */
    fun generate(data: ContractPdfData): File {
        require(data.contractNumber.isNotBlank()) {
            "Contract number cannot be blank."
        }

        require(data.landDescription.isNotBlank()) {
            "Land description cannot be blank."
        }

        require(data.landArea >= 0.0) {
            "Land area cannot be negative."
        }

        require(data.parties.isNotEmpty()) {
            "At least one party is required."
        }

        val outputDirectory = File(context.cacheDir, "contracts").apply {
            if (!exists() && !mkdirs()) {
                throw IllegalStateException(
                    "Unable to create contract output directory."
                )
            }
        }

        val safeContractNumber = data.contractNumber
            .replace(Regex("[^A-Za-z0-9._-]"), "_")

        val outputFile = File(
            outputDirectory,
            "contract_$safeContractNumber.pdf"
        )

        val document = PdfDocument()

        try {
            val pageWidth = PAGE_WIDTH
            val pageHeight = PAGE_HEIGHT

            val pageInfo = PdfDocument.PageInfo.Builder(
                pageWidth,
                pageHeight,
                1
            ).create()

            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            val content = PageRenderer(
                canvas = canvas,
                width = pageWidth,
                height = pageHeight
            )

            drawHeader(content, data)
            drawContractSummary(content, data)
            drawParties(content, data)
            drawLandAndCultivation(content, data)
            drawCosts(content, data)
            drawSettlement(content, data)
            drawNotes(content, data)
            drawFooter(content)

            document.finishPage(page)

            FileOutputStream(outputFile).use { output ->
                document.writeTo(output)
            }

            return outputFile
        } finally {
            document.close()
        }
    }

    private fun drawHeader(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.text(
            "SOCIAL CONTRACT",
            PaintStyle.HEADER
        )

        page.text(
            "Cultivation Contract",
            PaintStyle.SUBHEADER
        )

        page.horizontalLine()

        page.labelValue(
            "Contract No.",
            data.contractNumber
        )

        page.labelValue(
            "Created",
            formatDate(data.createdAt)
        )

        page.spacing()
    }

    private fun drawContractSummary(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.section("CONTRACT SUMMARY")

        page.labelValue(
            "Crop",
            data.cropName
        )

        page.labelValue(
            "Cultivation period",
            formatDateRange(data.startDate, data.endDate)
        )

        data.cultivationDurationDays?.let {
            page.labelValue(
                "Duration",
                "$it days"
            )
        }

        page.spacing()
    }

    private fun drawParties(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.section("PARTIES")

        data.parties.forEachIndexed { index, party ->
            page.text(
                "${index + 1}. ${party.name}",
                PaintStyle.BOLD
            )

            page.labelValue(
                "Role",
                party.role
            )

            party.phone?.let {
                page.labelValue("Phone", it)
            }

            party.address?.let {
                page.labelValue("Address", it)
            }

            page.spacingSmall()
        }

        page.spacing()
    }

    private fun drawLandAndCultivation(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.section("LAND & CULTIVATION")

        page.labelValue(
            "Land",
            data.landDescription
        )

        page.labelValue(
            "Area",
            formatNumber(data.landArea) + " ${data.landAreaUnit}"
        )

        page.labelValue(
            "Crop",
            data.cropName
        )

        data.expectedYield?.let { yield ->
            page.labelValue(
                "Expected yield",
                buildString {
                    append(formatNumber(yield))
                    data.yieldUnit?.let {
                        append(" ")
                        append(it)
                    }
                }
            )
        }

        page.spacing()
    }

    private fun drawCosts(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.section("COST OF CULTIVATION")

        if (data.costs.isEmpty()) {
            page.text(
                "No cultivation costs recorded.",
                PaintStyle.NORMAL
            )
            page.spacing()
            return
        }

        var total = 0.0

        data.costs.forEach { cost ->
            total += cost.amount

            val description = cost.description
                ?.takeIf { it.isNotBlank() }
                ?.let { " — $it" }
                ?: ""

            page.labelValue(
                cost.category,
                "${formatMoney(cost.amount, cost.currency)}$description"
            )
        }

        page.horizontalLine()

        page.labelValue(
            "Total cultivation cost",
            formatMoney(total, data.costs.first().currency)
        )

        page.spacing()
    }

    private fun drawSettlement(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        page.section("SETTLEMENT")

        data.expectedRevenue?.let {
            page.labelValue(
                "Expected revenue",
                formatMoney(it, "BDT")
            )
        }

        data.farmerSharePercent?.let {
            page.labelValue(
                "Cultivator share",
                formatPercent(it)
            )
        }

        data.landownerSharePercent?.let {
            page.labelValue(
                "Landowner share",
                formatPercent(it)
            )
        }

        if (
            data.farmerSharePercent != null &&
            data.landownerSharePercent != null
        ) {
            val total =
                data.farmerSharePercent +
                    data.landownerSharePercent

            if (kotlin.math.abs(total - 100.0) > 0.01) {
                page.text(
                    "Warning: recorded settlement shares do not total 100%.",
                    PaintStyle.WARNING
                )
            }
        }

        page.spacing()
    }

    private fun drawNotes(
        page: PageRenderer,
        data: ContractPdfData
    ) {
        data.notes
            ?.takeIf { it.isNotBlank() }
            ?.let {
                page.section("NOTES")
                page.wrappedText(
                    it,
                    PaintStyle.NORMAL
                )
                page.spacing()
            }
    }

    private fun drawFooter(page: PageRenderer) {
        page.footer(
            "Generated by Social Contract Android"
        )
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(date)
    }

    private fun formatDateRange(
        start: Date?,
        end: Date?
    ): String {
        return when {
            start != null && end != null ->
                "${formatDate(start)} – ${formatDate(end)}"

            start != null ->
                "From ${formatDate(start)}"

            end != null ->
                "Until ${formatDate(end)}"

            else ->
                "Not specified"
        }
    }

    private fun formatNumber(value: Double): String {
        return NumberFormat
            .getNumberInstance(Locale.getDefault())
            .apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 0
            }
            .format(value)
    }

    private fun formatMoney(
        amount: Double,
        currency: String
    ): String {
        return "$currency ${formatNumber(amount)}"
    }

    private fun formatPercent(value: Double): String {
        return "${formatNumber(value)}%"
    }

    private enum class PaintStyle {
        HEADER,
        SUBHEADER,
        SECTION,
        NORMAL,
        BOLD,
        WARNING
    }

    private class PageRenderer(
        private val canvas: android.graphics.Canvas,
        private val width: Int,
        private val height: Int
    ) {

        private var y = MARGIN_TOP

        private val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.NORMAL
            )
            textSize = 11f
        }

        private val boldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD
            )
            textSize = 11f
        }

        private val headerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD
            )
            textSize = 22f
        }

        private val subheaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.NORMAL
            )
            textSize = 14f
        }

        private val sectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD
            )
            textSize = 13f
        }

        private val warningPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD
            )
            textSize = 10f
        }

        fun text(
            value: String,
            style: PaintStyle
        ) {
            val paint = when (style) {
                PaintStyle.HEADER -> headerPaint
                PaintStyle.SUBHEADER -> subheaderPaint
                PaintStyle.SECTION -> sectionPaint
                PaintStyle.BOLD -> boldPaint
                PaintStyle.WARNING -> warningPaint
                PaintStyle.NORMAL -> normalPaint
            }

            canvas.drawText(
                value,
                MARGIN_LEFT,
                y,
                paint
            )

            y += when (style) {
                PaintStyle.HEADER -> 30f
                PaintStyle.SUBHEADER -> 22f
                else -> 17f
            }
        }

        fun section(title: String) {
            y += 6f

            canvas.drawText(
                title,
                MARGIN_LEFT,
                y,
                sectionPaint
            )

            y += 19f
        }

        fun labelValue(
            label: String,
            value: String
        ) {
            canvas.drawText(
                "$label:",
                MARGIN_LEFT,
                y,
                boldPaint
            )

            canvas.drawText(
                value,
                MARGIN_LEFT + LABEL_WIDTH,
                y,
                normalPaint
            )

            y += 17f
        }

        fun wrappedText(
            value: String,
            style: PaintStyle
        ) {
            val paint = when (style) {
                PaintStyle.BOLD -> boldPaint
                PaintStyle.WARNING -> warningPaint
                else -> normalPaint
            }

            val words = value.split(Regex("\\s+"))
            var line = ""

            words.forEach { word ->
                val candidate = if (line.isEmpty()) {
                    word
                } else {
                    "$line $word"
                }

                if (
                    paint.measureText(candidate) >
                    width - MARGIN_LEFT - MARGIN_RIGHT
                ) {
                    canvas.drawText(
                        line,
                        MARGIN_LEFT,
                        y,
                        paint
                    )
                    y += 15f
                    line = word
                } else {
                    line = candidate
                }
            }

            if (line.isNotEmpty()) {
                canvas.drawText(
                    line,
                    MARGIN_LEFT,
                    y,
                    paint
                )
                y += 15f
            }
        }

        fun horizontalLine() {
            y += 4f

            canvas.drawLine(
                MARGIN_LEFT,
                y,
                width - MARGIN_RIGHT,
                y,
                normalPaint
            )

            y += 10f
        }

        fun spacing() {
            y += 8f
        }

        fun spacingSmall() {
            y += 3f
        }

        fun footer(value: String) {
            val footerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                typeface = Typeface.create(
                    Typeface.DEFAULT,
                    Typeface.NORMAL
                )
                textSize = 8f
            }

            canvas.drawText(
                value,
                MARGIN_LEFT,
                height - MARGIN_BOTTOM,
                footerPaint
            )
        }
    }

    private companion object {
        const val PAGE_WIDTH = 595
        const val PAGE_HEIGHT = 842

        const val MARGIN_LEFT = 42f
        const val MARGIN_RIGHT = 42f
        const val MARGIN_TOP = 48f
        const val MARGIN_BOTTOM = 32f

        const val LABEL_WIDTH = 150f
    }
}
```

