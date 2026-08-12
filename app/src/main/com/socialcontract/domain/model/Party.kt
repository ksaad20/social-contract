package com.socialcontract.domain.model

data class Party(
    val id: String,
    val contractId: String,
    val name: String,
    val phoneNumber: String? = null,
    val address: String? = null,
    val role: String,
    val nationalIdReference: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
