package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "parties"
)
data class PartyEntity(
    @PrimaryKey
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
