package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lands")
data class LandEntity(
    @PrimaryKey
    val id: String,
    val contractId: String,
    val name: String,
    val area: Double,
    val areaUnit: String,
    val location: String? = null,
    val plotReference: String? = null,
    val soilType: String? = null,
    val irrigationAvailable: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
