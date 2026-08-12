package com.socialcontract.domain.usecase

import com.socialcontract.calculation.SettlementCalculator
import com.socialcontract.data.database.entities.SettlementEntity
import com.socialcontract.data.repository.ExpenseRepository
import com.socialcontract.data.repository.HarvestRepository
import com.socialcontract.data.repository.SettlementRepository
import java.util.UUID

class CalculateSettlementUseCase(
    private val expenseRepository: ExpenseRepository,
    private val harvestRepository: HarvestRepository,
    private val settlementRepository: SettlementRepository,
    private val settlementCalculator: SettlementCalculator
) {

    suspend operator fun invoke(
        contractId: String,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double,
        currency: String = "BDT"
    ): String {

        require(contractId.isNotBlank()) {
            "Contract ID cannot be blank."
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

        require(currency.isNotBlank()) {
            "Currency cannot be blank."
        }

        val totalRevenue =
            harvestRepository.getTotalValue(contractId)

        val totalExpenses =
            expenseRepository.getTotalExpenses(contractId)

        val result = settlementCalculator.calculate(
            totalRevenue = totalRevenue,
            totalExpenses = totalExpenses,
            ownerSharePercent = ownerSharePercent,
            cultivatorSharePercent = cultivatorSharePercent
        )

        val now = System.currentTimeMillis()
        val settlementId = UUID.randomUUID().toString()

        val settlement = SettlementEntity(
            id = settlementId,
            contractId = contractId,
            totalRevenue = result.totalRevenue,
            totalExpenses = result.totalExpenses,
            netValue = result.netValue,
            ownerSharePercent = result.ownerSharePercent,
            cultivatorSharePercent = result.cultivatorSharePercent,
            ownerAmount = result.ownerAmount,
            cultivatorAmount = result.cultivatorAmount,
            currency = currency.trim().uppercase(),
            status = "CALCULATED",
            calculatedAt = now
        )

        settlementRepository.saveSettlement(settlement)

        return settlementId
    }
}
