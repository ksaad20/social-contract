package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.data.database.entities.HarvestEntity
import kotlin.math.max

data class ProfitResult(
    val grossRevenue: Double,
    val totalExpenses: Double,
    val netProfit: Double,
    val profitMarginPercent: Double
)

class CalculateProfitUseCase {

    operator fun invoke(
        harvests: List<HarvestEntity>,
        expenses: List<ExpenseEntity>,
        currency: String
    ): ProfitResult {
        require(currency.isNotBlank()) {
            "Currency must not be blank."
        }

        val grossRevenue = harvests
            .filter { it.currency == currency }
            .sumOf { it.totalValue }

        val totalExpenses = expenses
            .filter { it.currency == currency }
            .sumOf { it.amount }

        val netProfit =
            grossRevenue - totalExpenses

        val profitMarginPercent =
            if (grossRevenue > 0.0) {
                (netProfit / grossRevenue) * 100.0
            } else {
                0.0
            }

        return ProfitResult(
            grossRevenue = grossRevenue,
            totalExpenses = totalExpenses,
            netProfit = netProfit,
            profitMarginPercent = profitMarginPercent
        )
    }

    fun calculatePerParty(
        harvests: List<HarvestEntity>,
        expenses: List<ExpenseEntity>,
        currency: String,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double
    ): PartyProfitResult {
        require(ownerSharePercent >= 0.0) {
            "Owner share cannot be negative."
        }

        require(cultivatorSharePercent >= 0.0) {
            "Cultivator share cannot be negative."
        }

        require(
            ownerSharePercent +
                cultivatorSharePercent == 100.0
        ) {
            "Party shares must total 100%."
        }

        val result = invoke(
            harvests = harvests,
            expenses = expenses,
            currency = currency
        )

        val ownerProfit =
            result.netProfit *
                (ownerSharePercent / 100.0)

        val cultivatorProfit =
            result.netProfit *
                (cultivatorSharePercent / 100.0)

        return PartyProfitResult(
            grossRevenue = result.grossRevenue,
            totalExpenses = result.totalExpenses,
            netProfit = result.netProfit,
            ownerProfit = ownerProfit,
            cultivatorProfit = cultivatorProfit,
            ownerSharePercent = ownerSharePercent,
            cultivatorSharePercent = cultivatorSharePercent
        )
    }
}

data class PartyProfitResult(
    val grossRevenue: Double,
    val totalExpenses: Double,
    val netProfit: Double,
    val ownerProfit: Double,
    val cultivatorProfit: Double,
    val ownerSharePercent: Double,
    val cultivatorSharePercent: Double
)
