package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.SettlementDao
import com.socialcontract.data.database.entities.SettlementEntity
import kotlinx.coroutines.flow.Flow

class SettlementRepository(
    private val settlementDao: SettlementDao
) {

    fun observeSettlements(
        contractId: String
    ): Flow<List<SettlementEntity>> {
        return settlementDao.observeByContractId(contractId)
    }

    suspend fun getSettlement(
        settlementId: String
    ): SettlementEntity? {
        return settlementDao.getById(settlementId)
    }

    suspend fun getLatestSettlement(
        contractId: String
    ): SettlementEntity? {
        return settlementDao.getLatestByContractId(contractId)
    }

    suspend fun saveSettlement(
        settlement: SettlementEntity
    ) {
        settlementDao.insert(settlement)
    }

    suspend fun updateSettlement(
        settlement: SettlementEntity
    ) {
        settlementDao.update(settlement)
    }

    suspend fun deleteSettlement(
        settlement: SettlementEntity
    ) {
        settlementDao.delete(settlement)
    }

    suspend fun deleteContractSettlements(
        contractId: String
    ) {
        settlementDao.deleteByContractId(contractId)
    }
}
