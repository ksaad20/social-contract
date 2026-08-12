package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenses: List<ExpenseEntity>)

    @Update
    suspend fun update(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query(
        """
        SELECT *
        FROM expenses
        WHERE contractId = :contractId
        ORDER BY expenseDate ASC, createdAt ASC
        """
    )
    fun observeByContractId(contractId: String): Flow<List<ExpenseEntity>>

    @Query(
        """
        SELECT *
        FROM expenses
        WHERE id = :expenseId
        LIMIT 1
        """
    )
    suspend fun getById(expenseId: String): ExpenseEntity?

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0)
        FROM expenses
        WHERE contractId = :contractId
        """
    )
    suspend fun getTotalByContractId(contractId: String): Double

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0)
        FROM expenses
        WHERE contractId = :contractId
        AND category = :category
        """
    )
    suspend fun getTotalByCategory(
        contractId: String,
        category: String
    ): Double

    @Query(
        """
        DELETE FROM expenses
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(contractId: String)
}
