package com.socialcontract.ui.harvest

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
fun RecordHarvestScreen(
    onBack: () -> Unit,
    onSaveHarvest: (
        cropName: String,
        quantity: Double,
        unit: String,
        pricePerUnit: Double,
        currency: String,
        harvestDate: Long,
        qualityGrade: String?,
        buyerReference: String?,
        notes: String?
    ) -> Unit
) {
    var cropName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("kg") }
    var pricePerUnit by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("BDT") }
    var harvestDate by remember { mutableStateOf("") }
    var qualityGrade by remember { mutableStateOf("") }
    var buyerReference by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Record Harvest")

        OutlinedTextField(
            value = cropName,
            onValueChange = { cropName = it },
            label = { Text("Crop name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = unit,
            onValueChange = { unit = it },
            label = { Text("Harvest unit") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pricePerUnit,
            onValueChange = { pricePerUnit = it },
            label = { Text("Price per unit (BDT)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Currency") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = harvestDate,
            onValueChange = { harvestDate = it },
            label = { Text("Harvest date (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = qualityGrade,
            onValueChange = { qualityGrade = it },
            label = { Text("Quality grade") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = buyerReference,
            onValueChange = { buyerReference = it },
            label = { Text("Buyer reference") },
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
                val parsedQuantity =
                    quantity.toDoubleOrNull()
                        ?: return@Button

                val parsedPrice =
                    pricePerUnit.toDoubleOrNull()
                        ?: return@Button

                val parsedDate =
                    harvestDate.toLongOrNull()
                        ?: return@Button

                if (
                    cropName.isBlank() ||
                    parsedQuantity <= 0.0 ||
                    parsedPrice < 0.0 ||
                    unit.isBlank()
                ) {
                    return@Button
                }

                onSaveHarvest(
                    cropName.trim(),
                    parsedQuantity,
                    unit.trim(),
                    parsedPrice,
                    currency.trim(),
                    parsedDate,
                    qualityGrade.trim().ifBlank { null },
                    buyerReference.trim().ifBlank { null },
                    notes.trim().ifBlank { null }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Harvest")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
