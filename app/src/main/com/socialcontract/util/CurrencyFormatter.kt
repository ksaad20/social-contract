package com.socialcontract.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {

    fun format(
        amount: Double,
        currency: String = "BDT"
    ): String {
        val formatter = NumberFormat.getCurrencyInstance(
            Locale("en", "BD")
        )

        formatter.currency = java.util.Currency.getInstance(
            currency.uppercase()
        )

        return formatter.format(amount)
    }

    fun formatAmount(
        amount: Double
    ): String {
        return NumberFormat.getNumberInstance(
            Locale.US
        ).format(amount)
    }
}
