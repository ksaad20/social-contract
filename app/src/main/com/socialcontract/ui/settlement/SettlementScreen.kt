package com.socialcontract.ui.settlement

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
import com.socialcontract.data.database.entities.SettlementEntity
import com.socialcontract.util.CurrencyFormatter
import com.socialcontract.util.DateFormatter

@Composable
fun SettlementScreen(
    settlements: List<SettlementEntity>,
    onCreateSettlement: () -> Unit,
    onSettlementSelected: (String) -> Unit
) {
    val totalSettled = settlements.sumOf {
        it.totalAmount
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Settlement",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Total settled: ${
                CurrencyFormatter.format(
                    totalSettled,
                    settlements.firstOrNull()?.currency ?: "BDT"
                )
            }",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onCreateSettlement,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Settlement")
        }

        if (settlements.isEmpty()) {
            Text("No settlements recorded yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = settlements,
                    key = { it.id }
                ) { settlement ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = settlement.status,
                                style = MaterialTheme
                                    .typography
                                    .titleMedium
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
                                "Owner amount: ${
                                    CurrencyFormatter.format(
                                        settlement.ownerAmount,
                                        settlement.currency
                                    )
                                }"
                            )

                            Text(
                                "Cultivator amount: ${
                                    CurrencyFormatter.format(
                                        settlement.cultivatorAmount,
                                        settlement.currency
                                    )
                                }"
                            )

                            Text(
                                "Date: ${
                                    DateFormatter.format(
                                        settlement.settlementDate
                                    )
                                }"
                            )

                            Button(
                                onClick = {
                                    onSettlementSelected(
                                        settlement.id
                                    )
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
