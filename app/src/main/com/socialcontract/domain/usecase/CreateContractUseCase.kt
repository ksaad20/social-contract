package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.repository.ContractRepository
import com.socialcontract.domain.enums.ContractStatus
import java.util.UUID

class CreateContractUseCase(
    private val contractRepository: ContractRepository
) {

    suspend operator fun invoke(
        title: String,
        startDate: Long,
        endDate: Long?,
        landArea: Double,
        landAreaUnit: String,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double,
        currency: String = "BDT"
    ): String {
        require(title.isNotBlank()) {
            "Contract title cannot be blank."
        }

        require(startDate > 0L) {
            "Start date must be valid."
        }

        endDate?.let {
            require(it >= startDate) {
                "End date cannot be before start date."
            }
        }

        require(landArea > 0.0) {
            "Land area must be greater than zero."
        }

        require(landAreaUnit.isNotBlank()) {
            "Land area unit cannot be blank."
        }

        require(ownerSharePercent >= 0.0) {
            "Owner share cannot be negative."
        }

        require(cultivatorSharePercent >= 0.0) {
            "Cultivator share cannot be negative."
        }

        require(
            ownerSharePercent + cultivatorSharePercent == 100.0
        ) {
            "Owner and cultivator shares must total 100%."
        }

        require(currency.isNotBlank()) {
            "Currency cannot be blank."
        }

        val now = System.currentTimeMillis()
        val contractId = UUID.randomUUID().toString()

        val contract = ContractEntity(
            id = contractId,
            title = title.trim(),
            status = ContractStatus.DRAFT.name,
            startDate = startDate,
            endDate = endDate,
            currency = currency.trim().uppercase(),
            landArea = landArea,
            landAreaUnit = landAreaUnit.trim(),
            ownerSharePercent = ownerSharePercent,
            cultivatorSharePercent = cultivatorSharePercent,
            createdAt = now,
            updatedAt = now
        )

        contractRepository.saveContract(contract)

        return contractId
    }
}
