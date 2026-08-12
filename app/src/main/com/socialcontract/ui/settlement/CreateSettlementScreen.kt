package com.socialcontract.ui.settlement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateSettlementScreen(
    onBack: () -> Unit,
    onSaveSettlement: (
        settlementDate: Long,
        totalAmount: Double,
        ownerAmount: Double,
        cultivatorAmount: Double,
        currency: String,
        notes: String?
    ) -> Unit
) {
    var settlementDate by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var ownerAmount by remember { mutableStateOf("") }
    var cultivatorAmount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("BDT") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Create Settlement")

        OutlinedTextField(
            value = settlementDate,
            onValueChange = { settlementDate = it },
            label = { Text("Settlement date (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it },
            label = { Text("Total amount (BDT)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ownerAmount,
            onValueChange = { ownerAmount = it },
            label = { Text("Landowner amount (BDT)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cultivatorAmount,
            onValueChange = { cultivatorAmount = it },
            label = { Text("Cultivator amount (BDT)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Currency") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val date =
                    settlementDate.toLongOrNull()
                        ?: return@Button

                val total =
                    totalAmount.toDoubleOrNull()
                        ?: return@Button

                val owner =
                    ownerAmount.toDoubleOrNull()
                        ?: return@Button

                val cultivator =
                    cultivatorAmount.toDoubleOrNull()
                        ?: return@Button

                if (
                    date <= 0L ||
                    total < 0.0 ||
                    owner < 0.0 ||
                    cultivator < 0.0 ||
                    currency.isBlank()
                ) {
                    return@Button
                }

                if (
                    kotlin.math.abs(
                        (owner + cultivator) - total
                    ) > 0.01
                ) {
                    return@Button
                }

                onSaveSettlement(
                    date,
                    total,
                    owner,
                    cultivator,
                    currency.trim(),
                    notes.trim().ifBlank { null }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Settlement")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
