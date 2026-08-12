package com.socialcontract.validation

import com.socialcontract.data.database.entities.ContractEntity

class ContractValidator {

    fun validate(contract: ContractEntity): List<String> {
        val errors = mutableListOf<String>()

        if (contract.id.isBlank()) {
            errors += "Contract ID cannot be blank."
        }

        if (contract.title.isBlank()) {
            errors += "Contract title cannot be blank."
        }

        if (contract.startDate <= 0L) {
            errors += "Start date must be valid."
        }

        contract.endDate?.let { endDate ->
            if (endDate < contract.startDate) {
                errors += "End date cannot be before start date."
            }
        }

        if (contract.landArea <= 0.0) {
            errors += "Land area must be greater than zero."
        }

        if (contract.landAreaUnit.isBlank()) {
            errors += "Land area unit cannot be blank."
        }

        if (contract.currency.isBlank()) {
            errors += "Currency cannot be blank."
        }

        if (contract.ownerSharePercent < 0.0) {
            errors += "Owner share cannot be negative."
        }

        if (contract.cultivatorSharePercent < 0.0) {
            errors += "Cultivator share cannot be negative."
        }

        val totalShare =
            contract.ownerSharePercent +
                contract.cultivatorSharePercent

        if (kotlin.math.abs(totalShare - 100.0) >= 0.000001) {
            errors += "Owner and cultivator shares must total 100%."
        }

        return errors
    }

    fun isValid(contract: ContractEntity): Boolean {
        return validate(contract).isEmpty()
    }

    fun requireValid(contract: ContractEntity) {
        val errors = validate(contract)

        require(errors.isEmpty()) {
            errors.joinToString(separator = " ")
        }
    }
}
