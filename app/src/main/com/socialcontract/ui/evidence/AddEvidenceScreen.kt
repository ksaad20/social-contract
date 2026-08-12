package com.socialcontract.ui.evidence

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
fun AddEvidenceScreen(
    onBack: () -> Unit,
    onSaveEvidence: (
        title: String,
        type: String,
        uri: String,
        capturedAt: Long,
        description: String?,
        relatedPartyId: String?,
        latitude: Double?,
        longitude: Double?
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("DOCUMENT") }
    var uri by remember { mutableStateOf("") }
    var capturedAt by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var relatedPartyId by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Add Evidence")

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Evidence type") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uri,
            onValueChange = { uri = it },
            label = { Text("File or URI") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = capturedAt,
            onValueChange = { capturedAt = it },
            label = { Text("Captured at (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = relatedPartyId,
            onValueChange = { relatedPartyId = it },
            label = { Text("Related party ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude (degrees)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude (degrees)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val parsedCapturedAt =
                    capturedAt.toLongOrNull()
                        ?: return@Button

                val parsedLatitude =
                    latitude
                        .takeIf { it.isNotBlank() }
                        ?.toDoubleOrNull()

                val parsedLongitude =
                    longitude
                        .takeIf { it.isNotBlank() }
                        ?.toDoubleOrNull()

                if (
                    title.isBlank() ||
                    type.isBlank() ||
                    uri.isBlank() ||
                    parsedCapturedAt <= 0L
                ) {
                    return@Button
                }

                onSaveEvidence(
                    title.trim(),
                    type.trim(),
                    uri.trim(),
                    parsedCapturedAt,
                    description.trim().ifBlank { null },
                    relatedPartyId.trim().ifBlank { null },
                    parsedLatitude,
                    parsedLongitude
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Evidence")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
