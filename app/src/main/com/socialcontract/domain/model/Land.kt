package com.socialcontract.domain.model

data class Land(
    val id: String,
    val contractId: String,
    val name: String,
    val area: Double,
    val areaUnit: String = "decimal",
    val location: String? = null,
    val plotReference: String? = null,
    val soilType: String? = null,
    val irrigationAvailable: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
