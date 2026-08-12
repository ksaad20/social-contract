package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.LandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LandDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(land: LandEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lands: List<LandEntity>)

    @Update
    suspend fun update(land: LandEntity)

    @Delete
    suspend fun delete(land: LandEntity)

    @Query(
        """
        SELECT *
        FROM lands
        WHERE contractId = :contractId
        ORDER BY createdAt ASC
        """
    )
    fun observeByContractId(contractId: String): Flow<List<LandEntity>>

    @Query(
        """
        SELECT *
        FROM lands
        WHERE id = :landId
        LIMIT 1
        """
    )
    suspend fun getById(landId: String): LandEntity?

    @Query(
        """
        DELETE FROM lands
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(contractId: String)
}
