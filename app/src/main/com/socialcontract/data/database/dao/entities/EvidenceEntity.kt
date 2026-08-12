package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evidence")
data class EvidenceEntity(
    @PrimaryKey
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
