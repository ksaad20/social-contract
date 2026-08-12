package com.socialcontract.domain.model

import com.socialcontract.domain.enums.ContractStatus

data class Contract(
    val id: String,
    val title: String,
    val status: ContractStatus,
    val startDate: Long,
    val endDate: Long?,
    val currency: String = "BDT",
    val landArea: Double = 0.0,
    val landAreaUnit: String = "decimal",
    val ownerSharePercent: Double = 50.0,
    val cultivatorSharePercent: Double = 50.0,
    val createdAt: Long,
    val updatedAt: Long
) {
    val durationMillis: Long?
        get() = endDate?.minus(startDate)

    val totalSharePercent: Double
        get() = ownerSharePercent + cultivatorSharePercent
}
