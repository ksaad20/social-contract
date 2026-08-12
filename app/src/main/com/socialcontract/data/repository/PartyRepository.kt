```kotlin id="j6v3qm"
package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.PartyDao
import com.socialcontract.data.database.entities.PartyEntity
import kotlinx.coroutines.flow.Flow

class PartyRepository(
    private val partyDao: PartyDao
) {

    fun observeAllParties(): Flow<List<PartyEntity>> =
        partyDao.observeAll()

    fun observePartiesByContractId(
        contractId: String
    ): Flow<List<PartyEntity>> =
        partyDao.observeByContractId(contractId)

    suspend fun getParty(
        partyId: String
    ): PartyEntity? =
        partyDao.getById(partyId)

    suspend fun insertParty(
        party: PartyEntity
    ) {
        partyDao.insert(party)
    }

    suspend fun updateParty(
        party: PartyEntity
    ) {
        partyDao.update(party)
    }

    suspend fun deleteParty(
        party: PartyEntity
    ) {
        partyDao.delete(party)
    }

    suspend fun deletePartyById(
        partyId: String
    ) {
        partyDao.deleteById(partyId)
    }
}
```

