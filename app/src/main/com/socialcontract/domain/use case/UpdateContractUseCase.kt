package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.repository.ContractRepository

class UpdateContractUseCase(
    private val contractRepository: ContractRepository
) {

    suspend operator fun invoke(
        contract: ContractEntity
    ) {
        require(contract.id.isNotBlank()) {
            "Contract ID cannot be blank."
        }

        require(contract.title.isNotBlank()) {
            "Contract title cannot be blank."
        }

        require(contract.startDate > 0L) {
            "Start date must be valid."
        }

        contract.endDate?.let { endDate ->
            require(endDate >= contract.startDate) {
                "End date cannot be before start date."
            }
        }

        require(contract.landArea > 0.0) {
            "Land area must be greater than zero."
        }

        require(contract.ownerSharePercent >= 0.0) {
            "Owner share cannot be negative."
        }

        require(contract.cultivatorSharePercent >= 0.0) {
            "Cultivator share cannot be negative."
        }

        require(
            kotlin.math.abs(
                (contract.ownerSharePercent +
                    contract.cultivatorSharePercent) - 100.0
            ) < 0.000001
        ) {
            "Owner and cultivator shares must total 100%."
        }

        val existingContract = contractRepository.getContract(contract.id)

        require(existingContract != null) {
            "Contract not found: ${contract.id}"
        }

        contractRepository.updateContract(
            contract.copy(
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}
