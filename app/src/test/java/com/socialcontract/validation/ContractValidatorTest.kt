package com.socialcontract.android.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContractValidatorTest {

    private val validator = ContractValidator()

    @Test
    fun `accepts valid contract`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertTrue(result)
    }

    @Test
    fun `rejects blank contract number`() {
        val result = validator.isValid(
            contractNumber = "",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank land description`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects zero land area`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 0.0,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects negative land area`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = -1.0,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank land area unit`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank crop name`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "",
            cultivatorName = "Abdul Karim",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank cultivator name`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "",
            landownerName = "Rahman Ali"
        )

        assertFalse(result)
    }

    @Test
    fun `rejects blank landowner name`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-001",
            landDescription = "North field",
            landArea = 2.5,
            landAreaUnit = "acre",
            cropName = "Rice",
            cultivatorName = "Abdul Karim",
            landownerName = ""
        )

        assertFalse(result)
    }

    @Test
    fun `accepts fractional land area`() {
        val result = validator.isValid(
            contractNumber = "SC-2026-002",
            landDescription = "West field",
            landArea = 0.25,
            landAreaUnit = "acre",
            cropName = "Wheat",
            cultivatorName = "Karim",
            landownerName = "Rahman"
        )

        assertTrue(result)
    }

    @Test
    fun `accepts whitespace around valid text after trimming`() {
        val result = validator.isValid(
            contractNumber = "  SC-2026-003  ",
            landDescription = "  East field  ",
            landArea = 1.0,
            landAreaUnit = " acre ",
            cropName = " Rice ",
            cultivatorName = " Karim ",
            landownerName = " Rahman "
        )

        assertTrue(result)
    }

    @Test
    fun `rejects contract when multiple required fields are invalid`() {
        val result = validator.isValid(
            contractNumber = "",
            landDescription = "",
            landArea = -5.0,
            landAreaUnit = "",
            cropName = "",
            cultivatorName = "",
            landownerName = ""
        )

        assertFalse(result)
    }
}
