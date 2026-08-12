package com.socialcontract.validation

import com.socialcontract.data.database.entities.ExpenseEntity

class ExpenseValidator {

    fun validate(expense: ExpenseEntity): List<String> {
        val errors = mutableListOf<String>()

        if (expense.id.isBlank()) {
            errors += "Expense ID cannot be blank."
        }

        if (expense.contractId.isBlank()) {
            errors += "Contract ID cannot be blank."
        }

        if (expense.category.isBlank()) {
            errors += "Expense category cannot be blank."
        }

        if (expense.description.isBlank()) {
            errors += "Expense description cannot be blank."
        }

        if (expense.amount < 0.0) {
            errors += "Expense amount cannot be negative."
        }

        if (expense.currency.isBlank()) {
            errors += "Currency cannot be blank."
        }

        expense.quantity?.let { quantity ->
            if (quantity < 0.0) {
                errors += "Expense quantity cannot be negative."
            }
        }

        if (expense.quantity != null && expense.quantityUnit.isNullOrBlank()) {
            errors += "Quantity unit is required when quantity is provided."
        }

        if (expense.expenseDate <= 0L) {
            errors += "Expense date must be valid."
        }

        return errors
    }

    fun isValid(expense: ExpenseEntity): Boolean {
        return validate(expense).isEmpty()
    }

    fun requireValid(expense: ExpenseEntity) {
        val errors = validate(expense)

        require(errors.isEmpty()) {
            errors.joinToString(separator = " ")
        }
    }
}
