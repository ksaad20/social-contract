package com.socialcontract.ui.cultivation

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
import com.socialcontract.data.database.entities.CultivationEntity

@Composable
fun CultivationScreen(
    cultivations: List<CultivationEntity>,
    onAddCultivation: (
        cropName: String,
        variety: String?,
        season: String?,
        startDate: Long,
        expectedHarvestDate: Long?,
        area: Double,
        areaUnit: String,
        expectedYield: Double?,
        expectedYieldUnit: String?,
        notes: String?
    ) -> Unit,
    onDeleteCultivation: (CultivationEntity) -> Unit
) {
    var cropName by remember { mutableStateOf("") }
    var variety by remember { mutableStateOf("") }
    var season by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var expectedHarvestDate by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var areaUnit by remember { mutableStateOf("decimal") }
    var expectedYield by remember { mutableStateOf("") }
    var expectedYieldUnit by remember { mutableStateOf("kg") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Cultivation")

        OutlinedTextField(
            value = cropName,
            onValueChange = { cropName = it },
            label = { Text("Crop name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = variety,
            onValueChange = { variety = it },
            label = { Text("Variety") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = season,
            onValueChange = { season = it },
            label = { Text("Season") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start date (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = expectedHarvestDate,
            onValueChange = { expectedHarvestDate = it },
            label = { Text("Expected harvest date (Unix ms)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = area,
            onValueChange = { area = it },
            label = { Text("Cultivated area") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = areaUnit,
            onValueChange = { areaUnit = it },
            label = { Text("Area unit") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = expectedYield,
            onValueChange = { expectedYield = it },
            label = { Text("Expected yield") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = expectedYieldUnit,
            onValueChange = { expectedYieldUnit = it },
            label = { Text("Yield unit") },
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
                val parsedStartDate =
                    startDate.toLongOrNull()
                        ?: return@Button

                val parsedArea =
                    area.toDoubleOrNull()
                        ?: return@Button

                val parsedExpectedHarvestDate =
                    expectedHarvestDate
                        .takeIf { it.isNotBlank() }
                        ?.toLongOrNull()

                val parsedExpectedYield =
                    expectedYield
                        .takeIf { it.isNotBlank() }
                        ?.toDoubleOrNull()

                if (cropName.isBlank() || parsedArea <= 0.0) {
                    return@Button
                }

                onAddCultivation(
                    cropName.trim(),
                    variety.trim().ifBlank { null },
                    season.trim().ifBlank { null },
                    parsedStartDate,
                    parsedExpectedHarvestDate,
                    parsedArea,
                    areaUnit.trim(),
                    parsedExpectedYield,
                    expectedYieldUnit.trim(),
                    notes.trim().ifBlank { null }
                )

                cropName = ""
                variety = ""
                season = ""
                startDate = ""
                expectedHarvestDate = ""
                area = ""
                expectedYield = ""
                notes = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Cultivation")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = cultivations,
                key = { it.id }
            ) { cultivation ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(cultivation.cropName)

                        cultivation.variety?.let {
                            Text("Variety: $it")
                        }

                        Text(
                            "Area: ${cultivation.area} " +
                                cultivation.areaUnit
                        )

                        cultivation.expectedYield?.let {
                            Text(
                                "Expected yield: $it " +
                                    "${cultivation.expectedYieldUnit ?: ""}"
                            )
                        }

                        Button(
                            onClick = {
                                onDeleteCultivation(cultivation)
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
