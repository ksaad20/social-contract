package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivation")
data class CultivationEntity(
    @PrimaryKey
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
