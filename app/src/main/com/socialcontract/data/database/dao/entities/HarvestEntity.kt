package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "harvests")
data class HarvestEntity(
    @PrimaryKey
    val id: String,
    val contractId: String,
    val cropName: String,
    val quantity: Double,
    val unit: String,
    val unitPrice: Double,
    val currency: String = "BDT",
    val totalValue: Double,
    val harvestDate: Long,
    val buyerName: String? = null,
    val notes: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
