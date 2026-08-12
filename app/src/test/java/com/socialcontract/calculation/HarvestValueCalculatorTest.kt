```kotlin
package com.socialcontract.android.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class HarvestValueCalculatorTest {

    private val calculator = HarvestValueCalculator()

    @Test
    fun `calculates harvest value from quantity and unit price`() {
        val result = calculator.calculateValue(
            harvestQuantity = 1_000.0,
            unitPrice = 50.0
        )

        assertEquals(
            50_000.0,
            result,
            0.001
        )
    }

    @Test
    fun `returns zero when harvest quantity is zero`() {
        val result = calculator.calculateValue(
            harvestQuantity = 0.0,
            unitPrice = 50.0
        )

        assertEquals(
            0.0,
            result,
            0.001
        )
    }

    @Test
    fun `returns zero when unit price is zero`() {
        val result = calculator.calculateValue(
            harvestQuantity = 1_000.0,
            unitPrice = 0.0
        )

        assertEquals(
            0.0,
            result,
            0.001
        )
    }

    @Test
    fun `handles decimal harvest quantities and prices`() {
        val result = calculator.calculateValue(
            harvestQuantity = 125.5,
            unitPrice = 42.75
        )

        assertEquals(
            5_365.125,
            result,
            0.001
        )
    }

    @Test
    fun `calculates large harvest value`() {
        val result = calculator.calculateValue(
            harvestQuantity = 25_000.0,
            unitPrice = 125.0
        )

        assertEquals(
            3_125_000.0,
            result,
            0.001
        )
    }

    @Test
    fun `calculates value for a single unit of harvest`() {
        val result = calculator.calculateValue(
            harvestQuantity = 1.0,
            unitPrice = 75.0
        )

        assertEquals(
            75.0,
            result,
            0.001
        )
    }
}
```

