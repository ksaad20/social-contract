```kotlin
package com.socialcontract.android.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class CostCalculatorTest {

    private val calculator = CostCalculator()

    @Test
    fun `calculates total cultivation cost`() {
        val result = calculator.calculateTotal(
            laborCost = 10_000.0,
            fertilizerCost = 5_000.0,
            seedCost = 3_000.0,
            irrigationCost = 2_000.0,
            communicationTransportationCost = 1_000.0,
            otherCosts = 500.0
        )

        assertEquals(
            21_500.0,
            result,
            0.001
        )
    }

    @Test
    fun `returns zero when all costs are zero`() {
        val result = calculator.calculateTotal(
            laborCost = 0.0,
            fertilizerCost = 0.0,
            seedCost = 0.0,
            irrigationCost = 0.0,
            communicationTransportationCost = 0.0,
            otherCosts = 0.0
        )

        assertEquals(
            0.0,
            result,
            0.001
        )
    }

    @Test
    fun `handles decimal cost values`() {
        val result = calculator.calculateTotal(
            laborCost = 1_250.50,
            fertilizerCost = 750.25,
            seedCost = 499.75,
            irrigationCost = 300.50,
            communicationTransportationCost = 199.00,
            otherCosts = 100.00
        )

        assertEquals(
            3_100.0,
            result,
            0.001
        )
    }

    @Test
    fun `calculates total when only one cost category is provided`() {
        val result = calculator.calculateTotal(
            laborCost = 5_000.0,
            fertilizerCost = 0.0,
            seedCost = 0.0,
            irrigationCost = 0.0,
            communicationTransportationCost = 0.0,
            otherCosts = 0.0
        )

        assertEquals(
            5_000.0,
            result,
            0.001
        )
    }

    @Test
    fun `calculates total for large cultivation costs`() {
        val result = calculator.calculateTotal(
            laborCost = 100_000.0,
            fertilizerCost = 50_000.0,
            seedCost = 25_000.0,
            irrigationCost = 20_000.0,
            communicationTransportationCost = 10_000.0,
            otherCosts = 5_000.0
        )

        assertEquals(
            210_000.0,
            result,
            0.001
        )
    }
}
```
