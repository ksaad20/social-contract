package com.socialcontract.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val contractId: String,
    val category: String,
    val description: String,
    val amount: Double,
    val currency: String = "BDT",
    val quantity: Double? = null,
    val quantityUnit: String? = null,
    val payerPartyId: String? = null,
    val expenseDate: Long,
    val receiptReference: String? = null,
    val notes: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
