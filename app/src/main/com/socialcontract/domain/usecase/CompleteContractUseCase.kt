package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.repository.ContractRepository
import com.socialcontract.domain.enums.ContractStatus

class CompleteContractUseCase(
    private val contractRepository: ContractRepository
) {

    suspend operator fun invoke(
        contractId: String
    ) {
        require(contractId.isNotBlank()) {
            "Contract ID cannot be blank."
        }

        val contract = contractRepository.getContract(contractId)
            ?: throw IllegalArgumentException(
                "Contract not found: $contractId"
            )

        require(contract.status != ContractStatus.CANCELLED.name) {
            "A cancelled contract cannot be completed."
        }

        require(contract.status != ContractStatus.COMPLETED.name) {
            "Contract is already completed."
        }

        val completedContract = contract.copy(
            status = ContractStatus.COMPLETED.name,
            endDate = contract.endDate
                ?: System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        contractRepository.updateContract(completedContract)
    }
}
