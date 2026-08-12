package com.socialcontract.ui.evidence

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
import com.socialcontract.data.database.entities.EvidenceEntity
import com.socialcontract.util.DateFormatter

@Composable
fun EvidenceScreen(
    evidence: List<EvidenceEntity>,
    onAddEvidence: () -> Unit,
    onEvidenceSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Evidence",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onAddEvidence,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Evidence")
        }

        if (evidence.isEmpty()) {
            Text("No evidence records yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = evidence,
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
                                text = item.title,
                                style = MaterialTheme
                                    .typography
                                    .titleMedium
                            )

                            Text("Type: ${item.type}")

                            Text(
                                "Captured: ${
                                    DateFormatter.format(
                                        item.capturedAt
                                    )
                                }"
                            )

                            item.description?.let {
                                Text(it)
                            }

                            Button(
                                onClick = {
                                    onEvidenceSelected(item.id)
                                }
                            ) {
                                Text("View")
                            }
                        }
                    }
                }
            }
        }
    }
}
