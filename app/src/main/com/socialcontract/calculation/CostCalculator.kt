package com.socialcontract.calculation

/**
 * Calculates cultivation costs from individual cost components.
 *
 * All monetary values are expressed in the same currency, normally BDT.
 */
class CostCalculator {

    data class CostBreakdown(
        val labor: Double = 0.0,
        val fertilizer: Double = 0.0,
        val seed: Double = 0.0,
        val irrigation: Double = 0.0,
        val pesticide: Double = 0.0,
        val equipment: Double = 0.0,
        val transportation: Double = 0.0,
        val communication: Double = 0.0,
        val landPreparation: Double = 0.0,
        val harvesting: Double = 0.0,
        val storage: Double = 0.0,
        val other: Double = 0.0
    ) {
        val total: Double
            get() = labor +
                fertilizer +
                seed +
                irrigation +
                pesticide +
                equipment +
                transportation +
                communication +
                landPreparation +
                harvesting +
                storage +
                other
    }

    fun calculate(
        labor: Double = 0.0,
        fertilizer: Double = 0.0,
        seed: Double = 0.0,
        irrigation: Double = 0.0,
        pesticide: Double = 0.0,
        equipment: Double = 0.0,
        transportation: Double = 0.0,
        communication: Double = 0.0,
        landPreparation: Double = 0.0,
        harvesting: Double = 0.0,
        storage: Double = 0.0,
        other: Double = 0.0
    ): CostBreakdown {
        val values = listOf(
            "labor" to labor,
            "fertilizer" to fertilizer,
            "seed" to seed,
            "irrigation" to irrigation,
            "pesticide" to pesticide,
            "equipment" to equipment,
            "transportation" to transportation,
            "communication" to communication,
            "landPreparation" to landPreparation,
            "harvesting" to harvesting,
            "storage" to storage,
            "other" to other
        )

        values.forEach { (name, value) ->
            require(value >= 0.0) {
                "$name cost cannot be negative."
            }
        }

        return CostBreakdown(
            labor = labor,
            fertilizer = fertilizer,
            seed = seed,
            irrigation = irrigation,
            pesticide = pesticide,
            equipment = equipment,
            transportation = transportation,
            communication = communication,
            landPreparation = landPreparation,
            harvesting = harvesting,
            storage = storage,
            other = other
        )
    }
}
