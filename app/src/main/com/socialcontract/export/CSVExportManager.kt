```kotlin
package com.socialcontract.android.contract

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

/**
 * Exports contract data to CSV.
 *
 * CSV is deliberately generated without external dependencies so the MVP
 * remains lightweight and works offline.
 */
class CsvExportManager(
    private val context: Context
) {

    /**
     * Exports the supplied contract data as a CSV file.
     */
    fun exportContract(
        data: PdfContractGenerator.ContractPdfData
    ): File {
        require(data.contractNumber.isNotBlank()) {
            "Contract number cannot be blank."
        }

        val directory = File(
            context.cacheDir,
            "exports"
        ).apply {
            if (!exists() && !mkdirs()) {
                throw IllegalStateException(
                    "Unable to create export directory."
                )
            }
        }

        val safeContractNumber = data.contractNumber
            .replace(Regex("[^A-Za-z0-9._-]"), "_")

        val file = File(
            directory,
            "contract_$safeContractNumber.csv"
        )

        FileOutputStream(file).use { output ->
            output.write(
                buildCsv(data).toByteArray(
                    StandardCharsets.UTF_8
                )
            )
        }

        return file
    }

    private fun buildCsv(
        data: PdfContractGenerator.ContractPdfData
    ): String {
        val builder = StringBuilder()

        builder.appendLine(
            "section,field,value,unit"
        )

        row(
            builder,
            "contract",
            "contract_number",
            data.contractNumber
        )

        row(
            builder,
            "contract",
            "created_at",
            data.createdAt.toString()
        )

        data.startDate?.let {
            row(
                builder,
                "contract",
                "start_date",
                it.toString()
            )
        }

        data.endDate?.let {
            row(
                builder,
                "contract",
                "end_date",
                it.toString()
            )
        }

        row(
            builder,
            "land",
            "description",
            data.landDescription
        )

        row(
            builder,
            "land",
            "area",
            data.landArea.toString(),
            data.landAreaUnit
        )

        row(
            builder,
            "cultivation",
            "crop",
            data.cropName
        )

        data.cultivationDurationDays?.let {
            row(
                builder,
                "cultivation",
                "duration",
                it.toString(),
                "days"
            )
        }

        data.expectedYield?.let {
            row(
                builder,
                "cultivation",
                "expected_yield",
                it.toString(),
                data.yieldUnit ?: ""
            )
        }

        data.expectedRevenue?.let {
            row(
                builder,
                "settlement",
                "expected_revenue",
                it.toString(),
                "BDT"
            )
        }

        data.farmerSharePercent?.let {
            row(
                builder,
                "settlement",
                "cultivator_share",
                it.toString(),
                "%"
            )
        }

        data.landownerSharePercent?.let {
            row(
                builder,
                "settlement",
                "landowner_share",
                it.toString(),
                "%"
            )
        }

        data.notes?.let {
            row(
                builder,
                "contract",
                "notes",
                it
            )
        }

        data.parties.forEachIndexed { index, party ->
            val prefix = "party_${index + 1}"

            row(
                builder,
                "party",
                "${prefix}_name",
                party.name
            )

            row(
                builder,
                "party",
                "${prefix}_role",
                party.role
            )

            party.phone?.let {
                row(
                    builder,
                    "party",
                    "${prefix}_phone",
                    it
                )
            }

            party.address?.let {
                row(
                    builder,
                    "party",
                    "${prefix}_address",
                    it
                )
            }
        }

        data.costs.forEachIndexed { index, cost ->
            val prefix = "cost_${index + 1}"

            row(
                builder,
                "cost",
                "${prefix}_category",
                cost.category
            )

            cost.description?.let {
                row(
                    builder,
                    "cost",
                    "${prefix}_description",
                    it
                )
            }

            row(
                builder,
                "cost",
                "${prefix}_amount",
                cost.amount.toString(),
                cost.currency
            )
        }

        return builder.toString()
    }

    private fun row(
        builder: StringBuilder,
        section: String,
        field: String,
        value: String,
        unit: String = ""
    ) {
        builder.append(
            csvEscape(section)
        )
        builder.append(',')

        builder.append(
            csvEscape(field)
        )
        builder.append(',')

        builder.append(
            csvEscape(value)
        )
        builder.append(',')

        builder.append(
            csvEscape(unit)
        )

        builder.append('\n')
    }

    /**
     * RFC-style CSV escaping:
     * - doubles embedded quotes
     * - wraps values containing commas, quotes, or line breaks
     */
    private fun csvEscape(value: String): String {
        val escaped = value.replace(
            "\"",
            "\"\""
        )

        return if (
            escaped.contains(',') ||
            escaped.contains('"') ||
            escaped.contains('\n') ||
            escaped.contains('\r')
        ) {
            "\"$escaped\""
        } else {
            escaped
        }
    }
}
```

