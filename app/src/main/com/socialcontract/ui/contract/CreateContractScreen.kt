package com.socialcontract.ui.contract

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
fun CreateContractScreen(
    onBack: () -> Unit,
    onContractCreated: (
        title: String,
        startDate: Long,
        endDate: Long?,
        landArea: Double,
        landAreaUnit: String,
        ownerSharePercent: Double,
        cultivatorSharePercent: Double,
        currency: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var landArea by remember { mutableStateOf("") }
    var landAreaUnit by remember { mutableStateOf("decimal") }
    var ownerShare by remember { mutableStateOf("50") }
    var cultivatorShare by remember { mutableStateOf("50") }
    var currency by remember { mutableStateOf("BDT") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create Contract")

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Contract title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start date (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("End date (Unix ms, optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = landArea,
            onValueChange = { landArea = it },
            label = { Text("Land area") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = landAreaUnit,
            onValueChange = { landAreaUnit = it },
            label = { Text("Land area unit") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ownerShare,
            onValueChange = { ownerShare = it },
            label = { Text("Landowner share (%)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cultivatorShare,
            onValueChange = { cultivatorShare = it },
            label = { Text("Cultivator share (%)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Currency") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val parsedStartDate = startDate.toLongOrNull()
                    ?: return@Button

                val parsedEndDate = endDate
                    .takeIf { it.isNotBlank() }
                    ?.toLongOrNull()

                val parsedLandArea = landArea.toDoubleOrNull()
                    ?: return@Button

                val parsedOwnerShare = ownerShare.toDoubleOrNull()
                    ?: return@Button

                val parsedCultivatorShare =
                    cultivatorShare.toDoubleOrNull()
                        ?: return@Button

                onContractCreated(
                    title,
                    parsedStartDate,
                    parsedEndDate,
                    parsedLandArea,
                    landAreaUnit,
                    parsedOwnerShare,
                    parsedCultivatorShare,
                    currency
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Contract")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
