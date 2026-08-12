```kotlin
package com.socialcontract.android.contract

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

/**
 * Shares generated contract files using Android's standard share sheet.
 *
 * The manager does not generate or modify contracts. It only exposes an
 * existing contract file to another application through a content URI.
 */
class ContractShareManager(
    private val context: Context
) {

    /**
     * Opens the Android share sheet for a contract PDF.
     *
     * @param pdfFile PDF file to share.
     * @param title Optional title displayed by the share sheet.
     */
    fun sharePdf(
        pdfFile: File,
        title: String = "Share cultivation contract"
    ) {
        require(pdfFile.exists()) {
            "Contract PDF does not exist: ${pdfFile.absolutePath}"
        }

        require(pdfFile.isFile) {
            "Contract PDF path is not a file: ${pdfFile.absolutePath}"
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TITLE, title)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(
            intent,
            title
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooser)
    }

    /**
     * Returns whether at least one installed application can receive
     * the supplied PDF.
     */
    fun canSharePdf(pdfFile: File): Boolean {
        if (!pdfFile.exists() || !pdfFile.isFile) {
            return false
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        return intent.resolveActivity(
            context.packageManager
        ) != null
    }
}
```

