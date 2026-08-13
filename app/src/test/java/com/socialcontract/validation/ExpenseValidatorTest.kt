package com.socialcontract.android.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExpenseValidatorTest {

    private val validator = ExpenseValidator()

    @Test
    fun `accepts valid expense`() {
        val result = validator.isValid(
            category = "Labor",
            amount = 10_000.0,
            currency = "BDT",
            description = "Field preparation"
        )

        assertTrue(result)
    }

    @Test
    fun `accepts expense without description`() {
        val result = validator.isValid(
            category = "Seeds",
            amount = 2_500.0,
            currency = "BDT",
            description = null
        )

        assertTrue(result)
    }

    @Test
    fun `rejects blank expense category`() {
        val result = validator.isValid(
            category = "",
            amount = 10_000.0,
            currency = "BDT",
            description = "Field preparation"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects zero expense amount`() {
        val result = validator.isValid(
            category = "Labor",
            amount = 0.0,
            currency = "BDT",
            description = "Field preparation"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects negative expense amount`() {
        val result = validator.isValid(
            category = "Fertilizer",
            amount = -500.0,
            currency = "BDT",
            description = "Urea"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank currency`() {
        val result = validator.isValid(
            category = "Irrigation",
            amount = 3_000.0,
            currency = "",
            description = "Water pump"
        )

        assertFalse(result)
    }

    @Test
    fun `accepts decimal expense amount`() {
        val result = validator.isValid(
            category = "Transportation",
            amount = 1_250.75,
            currency = "BDT",
            description = "Transport to market"
        )

        assertTrue(result)
    }

    @Test
    fun `accepts large expense amount`() {
        val result = validator.isValid(
            category = "Land preparation",
            amount = 1_000_000.0,
            currency = "BDT",
            description = "Large cultivation expense"
        )

        assertTrue(result)
    }

    @Test
    fun `accepts whitespace around valid fields`() {
        val result = validator.isValid(
            category = " Labor ",
            amount = 5_000.0,
            currency = " BDT ",
            description = " Field preparation "
        )

        assertTrue(result)
    }

    @Test
    fun `rejects expense when multiple required fields are invalid`() {
        val result = validator.isValid(
            category = "",
            amount = -100.0,
            currency = "",
            description = null
        )

        assertFalse(result)
    }

    @Test
    fun `accepts common cultivation expense categories`() {
        val categories = listOf(
            "Labor",
            "Fertilizer",
            "Seeds",
            "Irrigation",
            "Transportation",
            "Communication",
            "Machinery",
            "Pesticides",
            "Other"
        )

        categories.forEach { category ->
            assertTrue(
                "Expected category to be valid: $category",
                validator.isValid(
                    category = category,
                    amount = 1_000.0,
                    currency = "BDT",
                    description = null
                )
            )
        }
    }
}
