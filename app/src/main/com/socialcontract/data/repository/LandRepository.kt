```kotlin
package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.LandDao
import com.socialcontract.data.database.entities.LandEntity
import kotlinx.coroutines.flow.Flow

class LandRepository(
    private val landDao: LandDao
) {

    fun observeAllLand(): Flow<List<LandEntity>> =
        landDao.observeAll()

    fun observeLandByContractId(
        contractId: String
    ): Flow<List<LandEntity>> =
        landDao.observeByContractId(contractId)

    suspend fun getLand(
        landId: String
    ): LandEntity? =
        landDao.getById(landId)

    suspend fun insertLand(
        land: LandEntity
    ) {
        landDao.insert(land)
    }

    suspend fun updateLand(
        land: LandEntity
    ) {
        landDao.update(land)
    }

    suspend fun deleteLand(
        land: LandEntity
    ) {
        landDao.delete(land)
    }

    suspend fun deleteLandById(
        landId: String
    ) {
        landDao.deleteById(landId)
    }
}
```

