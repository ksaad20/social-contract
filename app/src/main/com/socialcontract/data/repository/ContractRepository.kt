package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.ContractDao
import com.socialcontract.data.database.entities.ContractEntity
import kotlinx.coroutines.flow.Flow

class ContractRepository(
    private val contractDao: ContractDao
) {

    fun observeContracts(): Flow<List<ContractEntity>> {
        return contractDao.observeAll()
    }

    fun observeContract(
        contractId: String
    ): Flow<ContractEntity?> {
        return contractDao.observeById(contractId)
    }

    suspend fun getContract(
        contractId: String
    ): ContractEntity? {
        return contractDao.getById(contractId)
    }

    suspend fun saveContract(
        contract: ContractEntity
    ) {
        contractDao.insert(contract)
    }

    suspend fun updateContract(
        contract: ContractEntity
    ) {
        contractDao.update(contract)
    }

    suspend fun deleteContract(
        contractId: String
    ) {
        contractDao.deleteById(contractId)
    }
}
