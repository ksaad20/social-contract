package com.socialcontract.validation

import com.socialcontract.data.database.entities.ExpenseEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExpenseValidatorTest {

    private val validator = ExpenseValidator()

    private fun createExpense(
        id: String = "exp-001",
        contractId: String = "contract-001",
        category: String = "Labor",
        description: String = "Field preparation",
        amount: Double = 10_000.0,
        currency: String = "BDT",
        quantity: Double? = null,
        quantityUnit: String? = null,
        expenseDate: Long = 1_700_000_000_000L
    ): ExpenseEntity {
        return ExpenseEntity(
            id = id,
            contractId = contractId,
            category = category,
            description = description,
            amount = amount,
            currency = currency,
            quantity = quantity,
            quantityUnit = quantityUnit,
            expenseDate = expenseDate
        )
    }

    @Test
    fun `accepts valid expense`() {
        val expense = createExpense()
        assertTrue(validator.isValid(expense))
        assertEquals(emptyList<String>(), validator.validate(expense))
    }

    @Test
    fun `rejects blank expense id`() {
        val expense = createExpense(id = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("ID") })
    }

    @Test
    fun `rejects blank contract id`() {
        val expense = createExpense(contractId = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("Contract ID") })
    }

    @Test
    fun `rejects blank category`() {
        val expense = createExpense(category = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("category") })
    }

    @Test
    fun `rejects blank description`() {
        val expense = createExpense(description = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("description") })
    }

    @Test
    fun `rejects negative amount`() {
        val expense = createExpense(amount = -100.0)
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("amount") })
    }

    @Test
    fun `accepts zero amount`() {
        val expense = createExpense(amount = 0.0)
        assertTrue(validator.isValid(expense))
    }

    @Test
    fun `rejects blank currency`() {
        val expense = createExpense(currency = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("Currency") })
    }

    @Test
    fun `rejects negative quantity`() {
        val expense = createExpense(quantity = -5.0, quantityUnit = "kg")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("quantity") })
    }

    @Test
    fun `rejects quantity without unit`() {
        val expense = createExpense(quantity = 10.0, quantityUnit = null)
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("unit") })
    }

    @Test
    fun `rejects quantity with blank unit`() {
        val expense = createExpense(quantity = 10.0, quantityUnit = "")
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("unit") })
    }

    @Test
    fun `accepts null quantity without unit`() {
        val expense = createExpense(quantity = null, quantityUnit = null)
        assertTrue(validator.isValid(expense))
    }

    @Test
    fun `rejects invalid expense date`() {
        val expense = createExpense(expenseDate = 0L)
        assertFalse(validator.isValid(expense))
        assertTrue(validator.validate(expense).any { it.contains("date") })
    }

    @Test
    fun `accumulates multiple validation errors`() {
        val expense = createExpense(
            id = "",
            contractId = "",
            category = "",
            description = "",
            amount = -1.0,
            currency = "",
            quantity = -1.0,
            quantityUnit = "",
            expenseDate = 0L
        )
        val errors = validator.validate(expense)
        assertEquals(9, errors.size)
        assertFalse(validator.isValid(expense))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `requireValid throws on invalid expense`() {
        val expense = createExpense(category = "")
        validator.requireValid(expense)
    }

    @Test
    fun `requireValid succeeds on valid expense`() {
        val expense = createExpense()
        validator.requireValid(expense) // Should not throw
    }
}
