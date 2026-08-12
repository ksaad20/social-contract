package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.EvidenceDao
import com.socialcontract.data.database.entities.EvidenceEntity
import kotlinx.coroutines.flow.Flow

class EvidenceRepository(
    private val evidenceDao: EvidenceDao
) {

    fun observeEvidence(
        contractId: String
    ): Flow<List<EvidenceEntity>> {
        return evidenceDao.observeByContractId(contractId)
    }

    suspend fun getEvidence(
        evidenceId: String
    ): EvidenceEntity? {
        return evidenceDao.getById(evidenceId)
    }

    suspend fun addEvidence(
        evidence: EvidenceEntity
    ) {
        evidenceDao.insert(evidence)
    }

    suspend fun updateEvidence(
        evidence: EvidenceEntity
    ) {
        evidenceDao.update(evidence)
    }

    suspend fun deleteEvidence(
        evidence: EvidenceEntity
    ) {
        evidenceDao.delete(evidence)
    }

    suspend fun deleteContractEvidence(
        contractId: String
    ) {
        evidenceDao.deleteByContractId(contractId)
    }
}
