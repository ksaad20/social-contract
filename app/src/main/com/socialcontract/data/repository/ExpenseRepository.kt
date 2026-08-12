package com.socialcontract.data.repository

import com.socialcontract.data.database.dao.ExpenseDao
import com.socialcontract.data.database.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    fun observeExpenses(
        contractId: String
    ): Flow<List<ExpenseEntity>> {
        return expenseDao.observeByContractId(contractId)
    }

    suspend fun getExpense(
        expenseId: String
    ): ExpenseEntity? {
        return expenseDao.getById(expenseId)
    }

    suspend fun addExpense(
        expense: ExpenseEntity
    ) {
        expenseDao.insert(expense)
    }

    suspend fun updateExpense(
        expense: ExpenseEntity
    ) {
        expenseDao.update(expense)
    }

    suspend fun deleteExpense(
        expense: ExpenseEntity
    ) {
        expenseDao.delete(expense)
    }

    suspend fun getTotalExpenses(
        contractId: String
    ): Double {
        return expenseDao.getTotalByContractId(contractId)
    }

    suspend fun getExpensesByCategory(
        contractId: String,
        category: String
    ): Double {
        return expenseDao.getTotalByCategory(
            contractId = contractId,
            category = category
        )
    }

    suspend fun deleteContractExpenses(
        contractId: String
    ) {
        expenseDao.deleteByContractId(contractId)
    }
}
