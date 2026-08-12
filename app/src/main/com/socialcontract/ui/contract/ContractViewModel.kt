package com.socialcontract.ui.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.repository.ContractRepository
import com.socialcontract.domain.enums.ContractStatus
import com.socialcontract.domain.usecase.CreateContractUseCase
import com.socialcontract.domain.usecase.UpdateContractUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ContractUiState(
    val contracts: List<ContractEntity> = emptyList(),
    val selectedContract: ContractEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ContractViewModel(
    private val contractRepository: ContractRepository,
    private val createContractUseCase: CreateContractUseCase,
    private val updateContractUseCase: UpdateContractUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContractUiState())
    val uiState: StateFlow<ContractUiState> = _uiState.asStateFlow()

    val contracts: StateFlow<List<ContractEntity>> =
        contractRepository
            .observeContracts()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun selectContract(contractId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val contract =
                    contractRepository.getContract(contractId)

                _uiState.value = _uiState.value.copy(
                    selectedContract = contract,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message ?: "Unable to load contract."
                )
            }
        }
    }

    fun createContract(
        title: String,
        startDate: Long,
        endDate: Long?,
        landArea: Double,
        landAreaUnit: String,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double,
        currency: String = "BDT",
        onCreated: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val contractId = createContractUseCase(
                    title = title,
                    startDate = startDate,
                    endDate = endDate,
                    landArea = landArea,
                    landAreaUnit = landAreaUnit,
                    ownerSharePercent = ownerSharePercent,
                    cultivatorSharePercent = cultivatorSharePercent,
                    currency = currency
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                onCreated(contractId)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message ?: "Unable to create contract."
                )
            }
        }
    }

    fun updateContract(contract: ContractEntity) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                updateContractUseCase(contract)

                _uiState.value = _uiState.value.copy(
                    selectedContract = contract.copy(
                        updatedAt = System.currentTimeMillis()
                    ),
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message ?: "Unable to update contract."
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
