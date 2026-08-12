package com.socialcontract.ui.land

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.LandEntity

@Composable
fun LandScreen(
    lands: List<LandEntity>,
    onAddLand: (
        name: String,
        area: Double,
        areaUnit: String,
        location: String?,
        plotReference: String?,
        soilType: String?,
        irrigationAvailable: Boolean
    ) -> Unit,
    onDeleteLand: (LandEntity) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var areaUnit by remember { mutableStateOf("decimal") }
    var location by remember { mutableStateOf("") }
    var plotReference by remember { mutableStateOf("") }
    var soilType by remember { mutableStateOf("") }
    var irrigation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Land")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Land name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = area,
            onValueChange = { area = it },
            label = { Text("Area") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = areaUnit,
            onValueChange = { areaUnit = it },
            label = { Text("Area unit") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = plotReference,
            onValueChange = { plotReference = it },
            label = { Text("Plot reference") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = soilType,
            onValueChange = { soilType = it },
            label = { Text("Soil type") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val parsedArea = area.toDoubleOrNull()
                    ?: return@Button

                if (name.isBlank() || parsedArea <= 0.0) {
                    return@Button
                }

                onAddLand(
                    name.trim(),
                    parsedArea,
                    areaUnit.trim(),
                    location.trim().ifBlank { null },
                    plotReference.trim().ifBlank { null },
                    soilType.trim().ifBlank { null },
                    irrigation
                )

                name = ""
                area = ""
                location = ""
                plotReference = ""
                soilType = ""
                irrigation = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Land")
        }

        Button(
            onClick = {
                irrigation = !irrigation
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (irrigation) {
                    "Irrigation: Available"
                } else {
                    "Irrigation: Not Available"
                }
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = lands,
                key = { it.id }
            ) { land ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(land.name)
                        Text(
                            "Area: ${land.area} ${land.areaUnit}"
                        )

                        land.location?.let {
                            Text("Location: $it")
                        }

                        land.plotReference?.let {
                            Text("Plot: $it")
                        }

                        Text(
                            if (land.irrigationAvailable) {
                                "Irrigation: Available"
                            } else {
                                "Irrigation: Not available"
                            }
                        )

                        Button(
                            onClick = {
                                onDeleteLand(land)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
