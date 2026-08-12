package com.socialcontract.domain.model

data class Settlement(
    val id: String,
    val contractId: String,
    val totalRevenue: Double,
    val totalExpenses: Double,
    val netValue: Double,
    val ownerSharePercent: Double,
    val cultivatorSharePercent: Double,
    val ownerAmount: Double,
    val cultivatorAmount: Double,
    val currency: String = "BDT",
    val status: String,
    val calculatedAt: Long,
    val notes: String? = null
)
