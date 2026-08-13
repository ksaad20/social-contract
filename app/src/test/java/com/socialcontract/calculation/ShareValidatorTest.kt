package com.socialcontract.android.calculation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShareValidatorTest {

    private val validator = ShareValidator()

    @Test
    fun `accepts shares totaling one hundred percent`() {
        assertTrue(
            validator.isValid(
                cultivatorSharePercent = 60.0,
                landownerSharePercent = 40.0
            )
        )
    }

    @Test
    fun `accepts equal fifty fifty shares`() {
        assertTrue(
            validator.isValid(
                cultivatorSharePercent = 50.0,
                landownerSharePercent = 50.0
            )
        )
    }

    @Test
    fun `rejects shares totaling less than one hundred percent`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 60.0,
                landownerSharePercent = 30.0
            )
        )
    }

    @Test
    fun `rejects shares totaling more than one hundred percent`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 70.0,
                landownerSharePercent = 40.0
            )
        )
    }

    @Test
    fun `accepts zero share for one party`() {
        assertTrue(
            validator.isValid(
                cultivatorSharePercent = 100.0,
                landownerSharePercent = 0.0
            )
        )

        assertTrue(
            validator.isValid(
                cultivatorSharePercent = 0.0,
                landownerSharePercent = 100.0
            )
        )
    }

    @Test
    fun `rejects negative cultivator share`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = -10.0,
                landownerSharePercent = 110.0
            )
        )
    }

    @Test
    fun `rejects negative landowner share`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 110.0,
                landownerSharePercent = -10.0
            )
        )
    }

    @Test
    fun `rejects cultivator share above one hundred percent`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 101.0,
                landownerSharePercent = 0.0
            )
        )
    }

    @Test
    fun `rejects landowner share above one hundred percent`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 0.0,
                landownerSharePercent = 101.0
            )
        )
    }

    @Test
    fun `accepts decimal shares totaling one hundred percent`() {
        assertTrue(
            validator.isValid(
                cultivatorSharePercent = 62.5,
                landownerSharePercent = 37.5
            )
        )
    }

    @Test
    fun `rejects shares outside valid range even when their sum is one hundred percent`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = -25.0,
                landownerSharePercent = 125.0
            )
        )
    }

    @Test
    fun `rejects values with small total deviation beyond tolerance`() {
        assertFalse(
            validator.isValid(
                cultivatorSharePercent = 60.0,
                landownerSharePercent = 39.0
            )
        )
    }
}
