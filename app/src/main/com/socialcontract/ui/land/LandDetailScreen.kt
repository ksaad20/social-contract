```kotlin
package com.socialcontract.ui.land

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.LandEntity
import com.socialcontract.util.DateFormatter

@Composable
fun LandDetailScreen(
    land: LandEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Land Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = land.description,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    "Area: ${land.area} ${land.areaUnit}"
                )

                land.locationDescription?.let {
                    Text("Location: $it")
                }

                land.latitude?.let {
                    Text("Latitude: $it°")
                }

                land.longitude?.let {
                    Text("Longitude: $it°")
                }

                Text(
                    "Added: ${
                        DateFormatter.format(
                            land.createdAt
                        )
                    }"
                )

                Text(
                    "Updated: ${
                        DateFormatter.format(
                            land.updatedAt
                        )
                    }"
                )
            }
        }

        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit")
        }

        Button(
            onClick = onDelete,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
```
