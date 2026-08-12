package com.socialcontract.ui.settlement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.SettlementEntity
import com.socialcontract.data.repository.SettlementRepository
import com.socialcontract.domain.usecase.CreateSettlementUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.abs

data class SettlementUiState(
    val selectedSettlement: SettlementEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SettlementViewModel(
    private val settlementRepository: SettlementRepository,
    private val createSettlementUseCase: CreateSettlementUseCase,
    private val contractId: String
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(SettlementUiState())

    val uiState: StateFlow<SettlementUiState> =
        _uiState.asStateFlow()

    val settlements: StateFlow<List<SettlementEntity>> =
        settlementRepository
            .observeSettlements(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun selectSettlement(
        settlementId: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val settlement =
                    settlementRepository
                        .getSettlement(settlementId)

                _uiState.value = _uiState.value.copy(
                    selectedSettlement = settlement,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to load settlement."
                )
            }
        }
    }

    fun createSettlement(
        settlementDate: Long,
        totalAmount: Double,
        ownerAmount: Double,
        cultivatorAmount: Double,
        currency: String,
        notes: String?,
        onCreated: (String) -> Unit = {}
    ) {
        if (
            settlementDate <= 0L ||
            totalAmount < 0.0 ||
            ownerAmount < 0.0 ||
            cultivatorAmount < 0.0 ||
            currency.isBlank()
        ) {
            setError("Invalid settlement data.")
            return
        }

        if (
            abs(
                (ownerAmount + cultivatorAmount) -
                    totalAmount
            ) > 0.01
        ) {
            setError(
                "Owner and cultivator amounts must equal total."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val settlementId =
                    createSettlementUseCase(
                        contractId = contractId,
                        settlementDate = settlementDate,
                        totalAmount = totalAmount,
                        ownerAmount = ownerAmount,
                        cultivatorAmount = cultivatorAmount,
                        currency = currency,
                        notes = notes
                    )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                onCreated(settlementId)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to create settlement."
                )
            }
        }
    }

    fun updateSettlement(
        settlement: SettlementEntity
    ) {
        if (
            settlement.totalAmount < 0.0 ||
            settlement.ownerAmount < 0.0 ||
            settlement.cultivatorAmount < 0.0 ||
            abs(
                (settlement.ownerAmount +
                    settlement.cultivatorAmount) -
                    settlement.totalAmount
            ) > 0.01
        ) {
            setError("Invalid settlement amounts.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val updated =
                    settlement.copy(
                        updatedAt = System.currentTimeMillis()
                    )

                settlementRepository
                    .updateSettlement(updated)

                _uiState.value = _uiState.value.copy(
                    selectedSettlement = updated,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to update settlement."
                )
            }
        }
    }

    fun markCompleted(
        settlement: SettlementEntity
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val completed =
                    settlement.copy(
                        status = "COMPLETED",
                        completedAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )

                settlementRepository
                    .updateSettlement(completed)

                _uiState.value = _uiState.value.copy(
                    selectedSettlement = completed,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to complete settlement."
                )
            }
        }
    }

    fun deleteSettlement(
        settlement: SettlementEntity,
        onDeleted: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                settlementRepository
                    .deleteSettlement(settlement)

                _uiState.value = SettlementUiState()

                onDeleted()
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to delete settlement."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    private fun setError(
        message: String
    ) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = message
        )
    }
}
