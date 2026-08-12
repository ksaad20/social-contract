package com.socialcontract.util

import java.text.NumberFormat
import java.util.Locale

object NumberFormatter {

    private val numberFormat: NumberFormat
        get() = NumberFormat.getNumberInstance(Locale.US)

    fun format(
        value: Double,
        decimalPlaces: Int = 2
    ): String {
        require(decimalPlaces >= 0) {
            "Decimal places cannot be negative."
        }

        return numberFormat.apply {
            minimumFractionDigits = decimalPlaces
            maximumFractionDigits = decimalPlaces
        }.format(value)
    }

    fun formatInteger(
        value: Double
    ): String {
        return numberFormat.apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 0
        }.format(value)
    }
}
