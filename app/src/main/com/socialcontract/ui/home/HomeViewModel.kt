package com.socialcontract.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.repository.ContractRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val contractCount: Int = 0,
    val activeContractCount: Int = 0,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val contractRepository: ContractRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        contractRepository
            .observeContracts()
            .map { contracts ->
                HomeUiState(
                    contractCount = contracts.size,
                    activeContractCount = contracts.count {
                        it.status == "ACTIVE"
                    },
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState()
            )
}
