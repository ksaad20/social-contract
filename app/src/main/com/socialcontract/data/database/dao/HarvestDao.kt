package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.HarvestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HarvestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(harvest: HarvestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(harvests: List<HarvestEntity>)

    @Update
    suspend fun update(harvest: HarvestEntity)

    @Delete
    suspend fun delete(harvest: HarvestEntity)

    @Query(
        """
        SELECT *
        FROM harvests
        WHERE contractId = :contractId
        ORDER BY harvestDate ASC, createdAt ASC
        """
    )
    fun observeByContractId(contractId: String): Flow<List<HarvestEntity>>

    @Query(
        """
        SELECT *
        FROM harvests
        WHERE id = :harvestId
        LIMIT 1
        """
    )
    suspend fun getById(harvestId: String): HarvestEntity?

    @Query(
        """
        SELECT COALESCE(SUM(quantity), 0)
        FROM harvests
        WHERE contractId = :contractId
        """
    )
    suspend fun getTotalQuantity(contractId: String): Double

    @Query(
        """
        SELECT COALESCE(SUM(totalValue), 0)
        FROM harvests
        WHERE contractId = :contractId
        """
    )
    suspend fun getTotalValue(contractId: String): Double

    @Query(
        """
        DELETE FROM harvests
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(contractId: String)
}
