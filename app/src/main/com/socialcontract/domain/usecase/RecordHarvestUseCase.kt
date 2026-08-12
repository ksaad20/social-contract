package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.HarvestEntity
import com.socialcontract.data.repository.HarvestRepository
import java.util.UUID

class RecordHarvestUseCase(
    private val harvestRepository: HarvestRepository
) {

    suspend operator fun invoke(
        contractId: String,
        cropName: String,
        quantity: Double,
        unit: String,
        unitPrice: Double,
        currency: String = "BDT",
        harvestDate: Long = System.currentTimeMillis(),
        buyerName: String? = null,
        notes: String? = null
    ): String {

        require(contractId.isNotBlank()) {
            "Contract ID cannot be blank."
        }

        require(cropName.isNotBlank()) {
            "Crop name cannot be blank."
        }

        require(quantity > 0.0) {
            "Harvest quantity must be greater than zero."
        }

        require(unit.isNotBlank()) {
            "Harvest unit cannot be blank."
        }

        require(unitPrice >= 0.0) {
            "Unit price cannot be negative."
        }

        require(currency.isNotBlank()) {
            "Currency cannot be blank."
        }

        require(harvestDate > 0L) {
            "Harvest date must be valid."
        }

        val harvestId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val totalValue = quantity * unitPrice

        val harvest = HarvestEntity(
            id = harvestId,
            contractId = contractId,
            cropName = cropName.trim(),
            quantity = quantity,
            unit = unit.trim(),
            unitPrice = unitPrice,
            currency = currency.trim().uppercase(),
            totalValue = totalValue,
            harvestDate = harvestDate,
            buyerName = buyerName?.trim(),
            notes = notes?.trim(),
            createdAt = now,
            updatedAt = now
        )

        harvestRepository.addHarvest(harvest)

        return harvestId
    }
}
