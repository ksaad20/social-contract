package com.socialcontract.ui.land

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.dao.LandDao
import com.socialcontract.data.database.entities.LandEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class LandViewModel(
    private val landDao: LandDao,
    private val contractId: String
) : ViewModel() {

    val lands: StateFlow<List<LandEntity>> =
        landDao
            .observeByContractId(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addLand(
        name: String,
        area: Double,
        areaUnit: String,
        location: String?,
        plotReference: String?,
        soilType: String?,
        irrigationAvailable: Boolean
    ) {
        if (name.isBlank() || area <= 0.0 || areaUnit.isBlank()) {
            return
        }

        viewModelScope.launch {
            val now = System.currentTimeMillis()

            landDao.insert(
                LandEntity(
                    id = UUID.randomUUID().toString(),
                    contractId = contractId,
                    name = name.trim(),
                    area = area,
                    areaUnit = areaUnit.trim(),
                    location = location?.trim(),
                    plotReference = plotReference?.trim(),
                    soilType = soilType?.trim(),
                    irrigationAvailable = irrigationAvailable,
                    createdAt = now,
                    updatedAt = now
                )
            )
        }
    }

    fun updateLand(
        land: LandEntity
    ) {
        viewModelScope.launch {
            landDao.update(
                land.copy(
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteLand(
        land: LandEntity
    ) {
        viewModelScope.launch {
            landDao.delete(land)
        }
    }
}
