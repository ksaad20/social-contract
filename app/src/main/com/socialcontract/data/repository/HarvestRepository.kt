package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.HarvestDao
import com.socialcontract.data.database.entities.HarvestEntity
import kotlinx.coroutines.flow.Flow

class HarvestRepository(
    private val harvestDao: HarvestDao
) {

    fun observeHarvests(
        contractId: String
    ): Flow<List<HarvestEntity>> {
        return harvestDao.observeByContractId(contractId)
    }

    suspend fun getHarvest(
        harvestId: String
    ): HarvestEntity? {
        return harvestDao.getById(harvestId)
    }

    suspend fun addHarvest(
        harvest: HarvestEntity
    ) {
        harvestDao.insert(harvest)
    }

    suspend fun updateHarvest(
        harvest: HarvestEntity
    ) {
        harvestDao.update(harvest)
    }

    suspend fun deleteHarvest(
        harvest: HarvestEntity
    ) {
        harvestDao.delete(harvest)
    }

    suspend fun getTotalQuantity(
        contractId: String
    ): Double {
        return harvestDao.getTotalQuantity(contractId)
    }

    suspend fun getTotalValue(
        contractId: String
    ): Double {
        return harvestDao.getTotalValue(contractId)
    }

    suspend fun deleteContractHarvests(
        contractId: String
    ) {
        harvestDao.deleteByContractId(contractId)
    }
}
