package com.socialcontract.ui.harvest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.HarvestEntity
import com.socialcontract.data.repository.HarvestRepository
import com.socialcontract.domain.usecase.RecordHarvestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HarvestUiState(
    val selectedHarvest: HarvestEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HarvestViewModel(
    private val harvestRepository: HarvestRepository,
    private val recordHarvestUseCase: RecordHarvestUseCase,
    private val contractId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(HarvestUiState())
    val uiState: StateFlow<HarvestUiState> =
        _uiState.asStateFlow()

    val harvests: StateFlow<List<HarvestEntity>> =
        harvestRepository
            .observeHarvests(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun selectHarvest(harvestId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val harvest =
                    harvestRepository.getHarvest(harvestId)

                _uiState.value = _uiState.value.copy(
                    selectedHarvest = harvest,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to load harvest."
                )
            }
        }
    }

    fun recordHarvest(
        cropName: String,
        quantity: Double,
        unit: String,
        pricePerUnit: Double,
        currency: String,
        harvestDate: Long,
        qualityGrade: String?,
        buyerReference: String?,
        notes: String?,
        onRecorded: (String) -> Unit = {}
    ) {
        if (
            cropName.isBlank() ||
            quantity <= 0.0 ||
            unit.isBlank() ||
            pricePerUnit < 0.0 ||
            currency.isBlank() ||
            harvestDate <= 0L
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid harvest data."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val harvestId = recordHarvestUseCase(
                    contractId = contractId,
                    cropName = cropName,
                    quantity = quantity,
                    unit = unit,
                    pricePerUnit = pricePerUnit,
                    currency = currency,
                    harvestDate = harvestDate,
                    qualityGrade = qualityGrade,
                    buyerReference = buyerReference,
                    notes = notes
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                onRecorded(harvestId)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to record harvest."
                )
            }
        }
    }

    fun updateHarvest(
        harvest: HarvestEntity
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                harvestRepository.updateHarvest(
                    harvest.copy(
                        updatedAt = System.currentTimeMillis()
                    )
                )

                _uiState.value = _uiState.value.copy(
                    selectedHarvest = harvest,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to update harvest."
                )
            }
        }
    }

    fun deleteHarvest(
        harvest: HarvestEntity,
        onDeleted: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                harvestRepository.deleteHarvest(harvest)

                _uiState.value = HarvestUiState()

                onDeleted()
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to delete harvest."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}
