package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.ContractEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContractDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contract: ContractEntity)

    @Update
    suspend fun update(contract: ContractEntity)

    @Delete
    suspend fun delete(contract: ContractEntity)

    @Query(
        """
        SELECT *
        FROM contracts
        ORDER BY createdAt DESC
        """
    )
    fun observeAll(): Flow<List<ContractEntity>>

    @Query(
        """
        SELECT *
        FROM contracts
        WHERE id = :contractId
        LIMIT 1
        """
    )
    suspend fun getById(contractId: String): ContractEntity?

    @Query(
        """
        SELECT *
        FROM contracts
        WHERE id = :contractId
        LIMIT 1
        """
    )
    fun observeById(contractId: String): Flow<ContractEntity?>

    @Query(
        """
        DELETE FROM contracts
        WHERE id = :contractId
        """
    )
    suspend fun deleteById(contractId: String)
}
