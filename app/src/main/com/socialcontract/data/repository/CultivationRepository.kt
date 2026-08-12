```kotlin id="r4m8tz"
package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.CultivationDao
import com.socialcontract.data.database.entities.CultivationEntity
import kotlinx.coroutines.flow.Flow

class CultivationRepository(
    private val cultivationDao: CultivationDao
) {

    fun observeAllCultivations(): Flow<List<CultivationEntity>> =
        cultivationDao.observeAll()

    fun observeCultivationsByContractId(
        contractId: String
    ): Flow<List<CultivationEntity>> =
        cultivationDao.observeByContractId(contractId)

    suspend fun getCultivation(
        cultivationId: String
    ): CultivationEntity? =
        cultivationDao.getById(cultivationId)

    suspend fun insertCultivation(
        cultivation: CultivationEntity
    ) {
        cultivationDao.insert(cultivation)
    }

    suspend fun updateCultivation(
        cultivation: CultivationEntity
    ) {
        cultivationDao.update(cultivation)
    }

    suspend fun deleteCultivation(
        cultivation: CultivationEntity
    ) {
        cultivationDao.delete(cultivation)
    }

    suspend fun deleteCultivationById(
        cultivationId: String
    ) {
        cultivationDao.deleteById(cultivationId)
    }
}
```

