package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contracts")
data class ContractEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val status: String,
    val startDate: Long,
    val endDate: Long?,
    val currency: String = "BDT",
    val landArea: Double = 0.0,
    val landAreaUnit: String = "decimal",
    val ownerSharePercent: Double = 50.0,
    val cultivatorSharePercent: Double = 50.0,
    val createdAt: Long,
    val updatedAt: Long
)
