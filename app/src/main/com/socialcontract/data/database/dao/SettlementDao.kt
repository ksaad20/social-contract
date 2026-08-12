package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.SettlementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettlementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settlement: SettlementEntity)

    @Update
    suspend fun update(settlement: SettlementEntity)

    @Delete
    suspend fun delete(settlement: SettlementEntity)

    @Query(
        """
        SELECT *
        FROM settlements
        WHERE contractId = :contractId
        ORDER BY calculatedAt DESC
        """
    )
    fun observeByContractId(
        contractId: String
    ): Flow<List<SettlementEntity>>

    @Query(
        """
        SELECT *
        FROM settlements
        WHERE id = :settlementId
        LIMIT 1
        """
    )
    suspend fun getById(
        settlementId: String
    ): SettlementEntity?

    @Query(
        """
        SELECT *
        FROM settlements
        WHERE contractId = :contractId
        ORDER BY calculatedAt DESC
        LIMIT 1
        """
    )
    suspend fun getLatestByContractId(
        contractId: String
    ): SettlementEntity?

    @Query(
        """
        DELETE FROM settlements
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(
        contractId: String
    )
}
