package com.socialcontract.ui.evidence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.EvidenceEntity
import com.socialcontract.data.repository.EvidenceRepository
import com.socialcontract.domain.usecase.AddEvidenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EvidenceUiState(
    val selectedEvidence: EvidenceEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class EvidenceViewModel(
    private val evidenceRepository: EvidenceRepository,
    private val addEvidenceUseCase: AddEvidenceUseCase,
    private val contractId: String
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(EvidenceUiState())

    val uiState: StateFlow<EvidenceUiState> =
        _uiState.asStateFlow()

    val evidence: StateFlow<List<EvidenceEntity>> =
        evidenceRepository
            .observeEvidence(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun selectEvidence(
        evidenceId: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val item =
                    evidenceRepository.getEvidence(evidenceId)

                _uiState.value = _uiState.value.copy(
                    selectedEvidence = item,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to load evidence."
                )
            }
        }
    }

    fun addEvidence(
        title: String,
        type: String,
        uri: String,
        capturedAt: Long,
        description: String?,
        relatedPartyId: String?,
        latitude: Double?,
        longitude: Double?,
        onCreated: (String) -> Unit = {}
    ) {
        if (
            title.isBlank() ||
            type.isBlank() ||
            uri.isBlank() ||
            capturedAt <= 0L
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid evidence data."
            )
            return
        }

        if (
            latitude != null &&
            latitude !in -90.0..90.0
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Latitude must be between -90 and 90."
            )
            return
        }

        if (
            longitude != null &&
            longitude !in -180.0..180.0
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage =
                    "Longitude must be between -180 and 180."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val evidenceId = addEvidenceUseCase(
                    contractId = contractId,
                    title = title,
                    type = type,
                    uri = uri,
                    capturedAt = capturedAt,
                    description = description,
                    relatedPartyId = relatedPartyId,
                    latitude = latitude,
                    longitude = longitude
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                onCreated(evidenceId)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to add evidence."
                )
            }
        }
    }

    fun updateEvidence(
        evidence: EvidenceEntity
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                evidenceRepository.updateEvidence(
                    evidence.copy(
                        updatedAt = System.currentTimeMillis()
                    )
                )

                _uiState.value = _uiState.value.copy(
                    selectedEvidence = evidence,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to update evidence."
                )
            }
        }
    }

    fun deleteEvidence(
        evidence: EvidenceEntity,
        onDeleted: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                evidenceRepository.deleteEvidence(evidence)

                _uiState.value = EvidenceUiState()

                onDeleted()
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to delete evidence."
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
