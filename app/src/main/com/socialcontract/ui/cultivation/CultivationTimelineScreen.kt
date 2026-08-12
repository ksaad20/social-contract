package com.socialcontract.ui.cultivation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.CultivationEntity
import com.socialcontract.util.DateFormatter

@Composable
fun CultivationTimelineScreen(
    cultivations: List<CultivationEntity>
) {
    val sortedCultivations = cultivations.sortedBy {
        it.startDate
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Cultivation Timeline",
            style = MaterialTheme.typography.headlineMedium
        )

        if (sortedCultivations.isEmpty()) {
            Text(
                text = "No cultivation records yet.",
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = sortedCultivations,
                key = { it.id }
            ) { cultivation ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = cultivation.cropName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        cultivation.variety?.let {
                            Text("Variety: $it")
                        }

                        cultivation.season?.let {
                            Text("Season: $it")
                        }

                        Text(
                            "Started: ${
                                DateFormatter.format(
                                    cultivation.startDate
                                )
                            }"
                        )

                        cultivation.expectedHarvestDate?.let {
                            Text(
                                "Expected harvest: ${
                                    DateFormatter.format(it)
                                }"
                            )
                        }

                        cultivation.actualHarvestDate?.let {
                            Text(
                                "Actual harvest: ${
                                    DateFormatter.format(it)
                                }"
                            )
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
                    }
                }
            }
        }
    }
}
