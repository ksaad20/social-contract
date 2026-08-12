package com.socialcontract.ui.harvest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.HarvestEntity
import com.socialcontract.util.CurrencyFormatter
import com.socialcontract.util.DateFormatter

@Composable
fun HarvestScreen(
    harvests: List<HarvestEntity>,
    onRecordHarvest: () -> Unit,
    onHarvestSelected: (String) -> Unit
) {
    val totalQuantity = harvests.sumOf { it.quantity }
    val totalValue = harvests.sumOf { it.totalValue }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Harvest",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Total quantity: $totalQuantity",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Total value: ${
                CurrencyFormatter.format(
                    totalValue,
                    harvests.firstOrNull()?.currency ?: "BDT"
                )
            }",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onRecordHarvest,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Record Harvest")
        }

        if (harvests.isEmpty()) {
            Text("No harvest records yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = harvests,
                    key = { it.id }
                ) { harvest ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = harvest.cropName,
                                style = MaterialTheme
                                    .typography
                                    .titleMedium
                            )

                            Text(
                                "Quantity: ${harvest.quantity} " +
                                    harvest.unit
                            )

                            Text(
                                "Price: ${
                                    CurrencyFormatter.format(
                                        harvest.pricePerUnit,
                                        harvest.currency
                                    )
                                } / ${harvest.unit}"
                            )

                            Text(
                                "Total value: ${
                                    CurrencyFormatter.format(
                                        harvest.totalValue,
                                        harvest.currency
                                    )
                                }"
                            )

                            Text(
                                "Date: ${
                                    DateFormatter.format(
                                        harvest.harvestDate
                                    )
                                }"
                            )

                            harvest.qualityGrade?.let {
                                Text("Quality: $it")
                            }

                            Button(
                                onClick = {
                                    onHarvestSelected(harvest.id)
                                }
                            ) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }
}
