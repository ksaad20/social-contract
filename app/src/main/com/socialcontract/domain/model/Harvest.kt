package com.socialcontract.domain.model

data class Harvest(
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
