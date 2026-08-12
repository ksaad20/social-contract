package com.socialcontract.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private const val DEFAULT_PATTERN = "dd MMM yyyy"

    fun format(
        timestamp: Long,
        pattern: String = DEFAULT_PATTERN
    ): String {
        require(timestamp > 0L) {
            "Timestamp must be greater than zero."
        }

        return SimpleDateFormat(
            pattern,
            Locale.getDefault()
        ).format(Date(timestamp))
    }

    fun formatDateTime(
        timestamp: Long
    ): String {
        return format(
            timestamp = timestamp,
            pattern = "dd MMM yyyy, hh:mm a"
        )
    }

    fun formatForStorage(
        timestamp: Long
    ): String {
        return format(
            timestamp = timestamp,
            pattern = "yyyy-MM-dd"
        )
    }
}
