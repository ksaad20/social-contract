package com.socialcontract.calculation

class ShareValidator {

    companion object {
        private const val TOTAL_SHARE = 100.0
        private const val EPSILON = 0.000001
    }

    fun isValid(
        ownerSharePercent: Double,
        cultivatorSharePercent: Double
    ): Boolean {
        if (ownerSharePercent < 0.0) return false
        if (cultivatorSharePercent < 0.0) return false

        return kotlin.math.abs(
            ownerSharePercent +
                cultivatorSharePercent -
                TOTAL_SHARE
        ) < EPSILON
    }

    fun requireValid(
        ownerSharePercent: Double,
        cultivatorSharePercent: Double
    ) {
        require(
            isValid(
                ownerSharePercent = ownerSharePercent,
                cultivatorSharePercent = cultivatorSharePercent
            )
        ) {
            "Owner and cultivator shares must be non-negative and total 100%."
        }
    }
}
