```kotlin id="7v5q2c"
package com.socialcontract.android.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class SettlementCalculatorTest {

    private val calculator = SettlementCalculator()

    @Test
    fun `calculates cultivator and landowner shares from net profit`() {
        val result = calculator.calculate(
            netProfit = 100_000.0,
            cultivatorSharePercent = 60.0,
            landownerSharePercent = 40.0
        )

        assertEquals(
            60_000.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            40_000.0,
            result.landownerShare,
            0.001
        )
    }

    @Test
    fun `calculates equal settlement shares`() {
        val result = calculator.calculate(
            netProfit = 100_000.0,
            cultivatorSharePercent = 50.0,
            landownerSharePercent = 50.0
        )

        assertEquals(
            50_000.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            50_000.0,
            result.landownerShare,
            0.001
        )
    }

    @Test
    fun `returns zero shares when net profit is zero`() {
        val result = calculator.calculate(
            netProfit = 0.0,
            cultivatorSharePercent = 60.0,
            landownerSharePercent = 40.0
        )

        assertEquals(
            0.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            0.0,
            result.landownerShare,
            0.001
        )
    }

    @Test
    fun `handles decimal settlement percentages`() {
        val result = calculator.calculate(
            netProfit = 10_000.0,
            cultivatorSharePercent = 62.5,
            landownerSharePercent = 37.5
        )

        assertEquals(
            6_250.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            3_750.0,
            result.landownerShare,
            0.001
        )
    }

    @Test
    fun `preserves total net profit after settlement`() {
        val netProfit = 250_000.0

        val result = calculator.calculate(
            netProfit = netProfit,
            cultivatorSharePercent = 65.0,
            landownerSharePercent = 35.0
        )

        assertEquals(
            netProfit,
            result.cultivatorShare + result.landownerShare,
            0.001
        )
    }

    @Test
    fun `calculates full settlement for cultivator`() {
        val result = calculator.calculate(
            netProfit = 75_000.0,
            cultivatorSharePercent = 100.0,
            landownerSharePercent = 0.0
        )

        assertEquals(
            75_000.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            0.0,
            result.landownerShare,
            0.001
        )
    }

    @Test
    fun `calculates full settlement for landowner`() {
        val result = calculator.calculate(
            netProfit = 75_000.0,
            cultivatorSharePercent = 0.0,
            landownerSharePercent = 100.0
        )

        assertEquals(
            0.0,
            result.cultivatorShare,
            0.001
        )

        assertEquals(
            75_000.0,
            result.landownerShare,
            0.001
        )
    }
}
```

