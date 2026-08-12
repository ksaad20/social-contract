package com.socialcontract.calculation

class HarvestValueCalculator {

    fun calculate(
        quantity: Double,
        unitPrice: Double
    ): Double {
        require(quantity >= 0.0) {
            "Harvest quantity cannot be negative."
        }

        require(unitPrice >= 0.0) {
            "Unit price cannot be negative."
        }

        return quantity * unitPrice
    }
}
