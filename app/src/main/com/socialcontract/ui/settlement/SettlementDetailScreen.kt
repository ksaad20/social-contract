package com.socialcontract.ui.settlement

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
import com.socialcontract.data.database.entities.SettlementEntity
import com.socialcontract.util.CurrencyFormatter
import com.socialcontract.util.DateFormatter

@Composable
fun SettlementDetailScreen(
    settlement: SettlementEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Settlement Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Status: ${settlement.status}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    "Date: ${
                        DateFormatter.format(
                            settlement.settlementDate
                        )
                    }"
                )

                Text(
                    "Total: ${
                        CurrencyFormatter.format(
                            settlement.totalAmount,
                            settlement.currency
                        )
                    }"
                )

                Text(
                    "Landowner: ${
                        CurrencyFormatter.format(
                            settlement.ownerAmount,
                            settlement.currency
                        )
                    }"
                )

                Text(
                    "Cultivator: ${
                        CurrencyFormatter.format(
                            settlement.cultivatorAmount,
                            settlement.currency
                        )
                    }"
                )

                settlement.notes?.let {
                    Text("Notes: $it")
                }
            }
        }

        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit")
        }

        if (settlement.status != "COMPLETED") {
            Button(
                onClick = onMarkCompleted,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mark Completed")
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
