```kotlin
package com.socialcontract.ui.party

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
import com.socialcontract.data.database.entities.PartyEntity
import com.socialcontract.util.DateFormatter

@Composable
fun PartyDetailScreen(
    party: PartyEntity,
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
            text = party.name,
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
                    text = "Role: ${party.role}",
                    style = MaterialTheme.typography.titleMedium
                )

                party.phone?.let {
                    Text("Phone: $it")
                }

                party.address?.let {
                    Text("Address: $it")
                }

                party.nationalIdReference?.let {
                    Text("National ID reference: $it")
                }

                Text(
                    "Added: ${
                        DateFormatter.format(
                            party.createdAt
                        )
                    }"
                )

                Text(
                    "Updated: ${
                        DateFormatter.format(
                            party.updatedAt
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
