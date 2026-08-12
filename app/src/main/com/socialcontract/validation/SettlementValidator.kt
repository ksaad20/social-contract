package com.socialcontract.validation

import com.socialcontract.data.database.entities.SettlementEntity

class SettlementValidator {

    fun validate(settlement: SettlementEntity): List<String> {
        val errors = mutableListOf<String>()

        if (settlement.id.isBlank()) {
            errors += "Settlement ID cannot be blank."
        }

        if (settlement.contractId.isBlank()) {
            errors += "Contract ID cannot be blank."
        }

        if (settlement.totalRevenue < 0.0) {
            errors += "Total revenue cannot be negative."
        }

        if (settlement.totalExpenses < 0.0) {
            errors += "Total expenses cannot be negative."
        }

        if (settlement.ownerSharePercent < 0.0) {
            errors += "Owner share cannot be negative."
        }

        if (settlement.cultivatorSharePercent < 0.0) {
            errors += "Cultivator share cannot be negative."
        }

        val totalShare =
            settlement.ownerSharePercent +
                settlement.cultivatorSharePercent

        if (kotlin.math.abs(totalShare - 100.0) >= 0.000001) {
            errors += "Owner and cultivator shares must total 100%."
        }

        if (settlement.currency.isBlank()) {
            errors += "Currency cannot be blank."
        }

        if (settlement.status.isBlank()) {
            errors += "Settlement status cannot be blank."
        }

        if (settlement.calculatedAt <= 0L) {
            errors += "Settlement calculation date must be valid."
        }

        val calculatedNetValue =
            settlement.totalRevenue - settlement.totalExpenses

        if (kotlin.math.abs(
                settlement.netValue - calculatedNetValue
            ) >= 0.000001
        ) {
            errors += "Net value does not match revenue minus expenses."
        }

        val calculatedOwnerAmount =
            settlement.netValue *
                settlement.ownerSharePercent / 100.0

        if (kotlin.math.abs(
                settlement.ownerAmount - calculatedOwnerAmount
            ) >= 0.000001
        ) {
            errors += "Owner settlement amount is inconsistent with the share."
        }

        val calculatedCultivatorAmount =
            settlement.netValue *
                settlement.cultivatorSharePercent / 100.0

        if (kotlin.math.abs(
                settlement.cultivatorAmount - calculatedCultivatorAmount
            ) >= 0.000001
        ) {
            errors += "Cultivator settlement amount is inconsistent with the share."
        }

        return errors
    }

    fun isValid(settlement: SettlementEntity): Boolean {
        return validate(settlement).isEmpty()
    }

    fun requireValid(settlement: SettlementEntity) {
        val errors = validate(settlement)

        require(errors.isEmpty()) {
            errors.joinToString(separator = " ")
        }
    }
}
