package com.socialcontract.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class DashboardUiModel(
    val contractCount: Int,
    val activeContractCount: Int,
    val totalLandArea: Double,
    val totalLandAreaUnit: String,
    val totalExpenses: Double,
    val expenseCurrency: String,
    val totalHarvestQuantity: Double,
    val harvestUnit: String,
    val totalHarvestValue: Double,
    val harvestCurrency: String
)

@Composable
fun DashboardScreen(
    data: DashboardUiModel,
    onContracts: () -> Unit,
    onExpenses: () -> Unit,
    onHarvest: () -> Unit,
    onSettlements: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DashboardMetricCard(
                    title = "Contracts",
                    value = data.contractCount.toString(),
                    detail = "${data.activeContractCount} active"
                )
            }

            item {
                DashboardMetricCard(
                    title = "Land",
                    value = data.totalLandArea.toString(),
                    detail = data.totalLandAreaUnit
                )
            }

            item {
                DashboardMetricCard(
                    title = "Cultivation expenses",
                    value = data.totalExpenses.toString(),
                    detail = data.expenseCurrency
                )
            }

            item {
                DashboardMetricCard(
                    title = "Harvest quantity",
                    value = data.totalHarvestQuantity.toString(),
                    detail = data.harvestUnit
                )
            }

            item {
                DashboardMetricCard(
                    title = "Harvest value",
                    value = data.totalHarvestValue.toString(),
                    detail = data.harvestCurrency
                )
            }

            item {
                Button(
                    onClick = onContracts,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Contracts")
                }
            }

            item {
                Button(
                    onClick = onExpenses,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Expenses")
                }
            }

            item {
                Button(
                    onClick = onHarvest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Harvest")
                }
            }

            item {
                Button(
                    onClick = onSettlements,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Settlements")
                }
            }
        }
    }
}

@Composable
private fun DashboardMetricCard(
    title: String,
    value: String,
    detail: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = detail,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
