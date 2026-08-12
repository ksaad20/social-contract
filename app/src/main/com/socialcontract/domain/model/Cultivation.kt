package com.socialcontract.domain.model

data class Cultivation(
    val id: String,
    val contractId: String,
    val cropName: String,
    val variety: String? = null,
    val season: String? = null,
    val startDate: Long,
    val expectedHarvestDate: Long? = null,
    val actualHarvestDate: Long? = null,
    val area: Double,
    val areaUnit: String = "decimal",
    val expectedYield: Double? = null,
    val expectedYieldUnit: String? = null,
    val notes: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
