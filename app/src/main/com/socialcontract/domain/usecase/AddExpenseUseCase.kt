package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.data.repository.ExpenseRepository
import java.util.UUID

class AddExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(
        contractId: String,
        category: String,
        description: String,
        amount: Double,
        currency: String = "BDT",
        quantity: Double? = null,
        quantityUnit: String? = null,
        payerPartyId: String? = null,
        expenseDate: Long = System.currentTimeMillis(),
        receiptReference: String? = null,
        notes: String? = null
    ): String {

        require(contractId.isNotBlank()) {
            "Contract ID cannot be blank."
        }

        require(category.isNotBlank()) {
            "Expense category cannot be blank."
        }

        require(description.isNotBlank()) {
            "Expense description cannot be blank."
        }

        require(amount >= 0.0) {
            "Expense amount cannot be negative."
        }

        require(currency.isNotBlank()) {
            "Currency cannot be blank."
        }

        quantity?.let {
            require(it >= 0.0) {
                "Expense quantity cannot be negative."
            }
        }

        require(expenseDate > 0L) {
            "Expense date must be valid."
        }

        val expenseId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val expense = ExpenseEntity(
            id = expenseId,
            contractId = contractId,
            category = category.trim(),
            description = description.trim(),
            amount = amount,
            currency = currency.trim().uppercase(),
            quantity = quantity,
            quantityUnit = quantityUnit?.trim(),
            payerPartyId = payerPartyId?.trim(),
            expenseDate = expenseDate,
            receiptReference = receiptReference?.trim(),
            notes = notes?.trim(),
            createdAt = now,
            updatedAt = now
        )

        expenseRepository.addExpense(expense)

        return expenseId
    }
}
