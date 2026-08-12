package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.EvidenceEntity
import com.socialcontract.data.repository.EvidenceRepository
import java.util.UUID

class AddEvidenceUseCase(
    private val evidenceRepository: EvidenceRepository
) {

    suspend operator fun invoke(
        contractId: String,
        type: String,
        title: String,
        description: String? = null,
        fileUri: String? = null,
        capturedAt: Long = System.currentTimeMillis()
    ): String {

        require(contractId.isNotBlank()) {
            "Contract ID cannot be blank."
        }

        require(type.isNotBlank()) {
            "Evidence type cannot be blank."
        }

        require(title.isNotBlank()) {
            "Evidence title cannot be blank."
        }

        require(capturedAt > 0L) {
            "Evidence capture date must be valid."
        }

        fileUri?.let {
            require(it.isNotBlank()) {
                "Evidence file URI cannot be blank."
            }
        }

        val evidenceId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val evidence = EvidenceEntity(
            id = evidenceId,
            contractId = contractId,
            type = type.trim(),
            title = title.trim(),
            description = description?.trim(),
            fileUri = fileUri?.trim(),
            capturedAt = capturedAt,
            createdAt = now,
            updatedAt = now
        )

        evidenceRepository.addEvidence(evidence)

        return evidenceId
    }
}
