package com.socialcontract.calculation

class SettlementCalculator {

    data class SettlementResult(
        val totalRevenue: Double,
        val totalExpenses: Double,
        val netValue: Double,
        val ownerSharePercent: Double,
        val cultivatorSharePercent: Double,
        val ownerAmount: Double,
        val cultivatorAmount: Double
    )

    fun calculate(
        totalRevenue: Double,
        totalExpenses: Double,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double
    ): SettlementResult {
        require(totalRevenue >= 0.0) {
            "Total revenue cannot be negative."
        }

        require(totalExpenses >= 0.0) {
            "Total expenses cannot be negative."
        }

        require(ownerSharePercent >= 0.0) {
            "Owner share cannot be negative."
        }

        require(cultivatorSharePercent >= 0.0) {
            "Cultivator share cannot be negative."
        }

        require(
            kotlin.math.abs(
                ownerSharePercent + cultivatorSharePercent - 100.0
            ) < 0.000001
        ) {
            "Owner and cultivator shares must total 100%."
        }

        val netValue = totalRevenue - totalExpenses

        val ownerAmount =
            netValue * ownerSharePercent / 100.0

        val cultivatorAmount =
            netValue * cultivatorSharePercent / 100.0

        return SettlementResult(
            totalRevenue = totalRevenue,
            totalExpenses = totalExpenses,
            netValue = netValue,
            ownerSharePercent = ownerSharePercent,
            cultivatorSharePercent = cultivatorSharePercent,
            ownerAmount = ownerAmount,
            cultivatorAmount = cultivatorAmount
        )
    }
}
