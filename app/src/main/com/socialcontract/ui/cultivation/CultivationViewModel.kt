package com.socialcontract.ui.cultivation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.dao.CultivationDao
import com.socialcontract.data.database.entities.CultivationEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class CultivationViewModel(
    private val cultivationDao: CultivationDao,
    private val contractId: String
) : ViewModel() {

    val cultivations: StateFlow<List<CultivationEntity>> =
        cultivationDao
            .observeByContractId(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addCultivation(
        cropName: String,
        variety: String?,
        season: String?,
        startDate: Long,
        expectedHarvestDate: Long?,
        area: Double,
        areaUnit: String,
        expectedYield: Double?,
        expectedYieldUnit: String?,
        notes: String?
    ) {
        if (
            cropName.isBlank() ||
            startDate <= 0L ||
            area <= 0.0 ||
            areaUnit.isBlank()
        ) {
            return
        }

        if (
            expectedHarvestDate != null &&
            expectedHarvestDate < startDate
        ) {
            return
        }

        if (expectedYield != null && expectedYield < 0.0) {
            return
        }

        viewModelScope.launch {
            val now = System.currentTimeMillis()

            cultivationDao.insert(
                CultivationEntity(
                    id = UUID.randomUUID().toString(),
                    contractId = contractId,
                    cropName = cropName.trim(),
                    variety = variety?.trim(),
                    season = season?.trim(),
                    startDate = startDate,
                    expectedHarvestDate = expectedHarvestDate,
                    actualHarvestDate = null,
                    area = area,
                    areaUnit = areaUnit.trim(),
                    expectedYield = expectedYield,
                    expectedYieldUnit =
                        expectedYieldUnit?.trim(),
                    notes = notes?.trim(),
                    createdAt = now,
                    updatedAt = now
                )
            )
        }
    }

    fun updateCultivation(
        cultivation: CultivationEntity
    ) {
        viewModelScope.launch {
            cultivationDao.update(
                cultivation.copy(
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun recordActualHarvestDate(
        cultivation: CultivationEntity,
        harvestDate: Long
    ) {
        if (harvestDate < cultivation.startDate) {
            return
        }

        viewModelScope.launch {
            cultivationDao.update(
                cultivation.copy(
                    actualHarvestDate = harvestDate,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteCultivation(
        cultivation: CultivationEntity
    ) {
        viewModelScope.launch {
            cultivationDao.delete(cultivation)
        }
    }
}
