package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.PartyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(party: PartyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(parties: List<PartyEntity>)

    @Update
    suspend fun update(party: PartyEntity)

    @Delete
    suspend fun delete(party: PartyEntity)

    @Query(
        """
        SELECT *
        FROM parties
        WHERE contractId = :contractId
        ORDER BY name ASC
        """
    )
    fun observeByContractId(contractId: String): Flow<List<PartyEntity>>

    @Query(
        """
        SELECT *
        FROM parties
        WHERE id = :partyId
        LIMIT 1
        """
    )
    suspend fun getById(partyId: String): PartyEntity?

    @Query(
        """
        DELETE FROM parties
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(contractId: String)
}
