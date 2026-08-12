package com.socialcontract.domain.model

data class Evidence(
    val id: String,
    val contractId: String,
    val type: String,
    val title: String,
    val description: String? = null,
    val fileUri: String? = null,
    val capturedAt: Long,
    val createdAt: Long,
    val updatedAt: Long
)
