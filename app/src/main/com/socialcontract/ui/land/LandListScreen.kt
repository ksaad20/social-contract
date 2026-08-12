```kotlin
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.LandEntity

@Composable
fun LandListScreen(
    land: List<LandEntity>,
    onAddLand: () -> Unit,
    onLandSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Land",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onAddLand,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Land")
        }

        if (land.isEmpty()) {
            Text("No land records yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = land,
                    key = { it.id }
                ) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = item.description,
                                style = MaterialTheme
                                    .typography
                                    .titleMedium
                            )

                            Text(
                                "Area: ${item.area} " +
                                    item.areaUnit
                            )

                            item.locationDescription?.let {
                                Text("Location: $it")
                            }

                            if (
                                item.latitude != null &&
                                item.longitude != null
                            ) {
                                Text(
                                    "Coordinates: " +
                                        "${item.latitude}, " +
                                        item.longitude
                                )
                            }

                            Button(
                                onClick = {
                                    onLandSelected(item.id)
                                },
                                modifier = Modifier.fillMaxWidth()
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
```

