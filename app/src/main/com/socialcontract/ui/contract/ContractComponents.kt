package com.socialcontract.ui.contract

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContractSummaryCard(
    title: String,
    status: String,
    landArea: Double,
    landAreaUnit: String,
    ownerSharePercent: Double,
    cultivatorSharePercent: Double,
    currency: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            ContractInfoRow(
                label = "Status",
                value = status
            )

            ContractInfoRow(
                label = "Land area",
                value = "$landArea $landAreaUnit"
            )

            ContractInfoRow(
                label = "Landowner share",
                value = "$ownerSharePercent%"
            )

            ContractInfoRow(
                label = "Cultivator share",
                value = "$cultivatorSharePercent%"
            )

            ContractInfoRow(
                label = "Currency",
                value = currency
            )
        }
    }
}

@Composable
fun ContractInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
