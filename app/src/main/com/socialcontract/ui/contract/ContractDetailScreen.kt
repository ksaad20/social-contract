package com.socialcontract.ui.contract

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.ContractEntity

@Composable
fun ContractDetailScreen(
    contract: ContractEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onManageParties: () -> Unit,
    onManageLand: () -> Unit,
    onManageCultivation: () -> Unit,
    onManageExpenses: () -> Unit,
    onManageHarvest: () -> Unit,
    onManageEvidence: () -> Unit,
    onSettlement: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = contract.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Status: ${contract.status}")
                Text(
                    "Land: ${contract.landArea} ${contract.landAreaUnit}"
                )
                Text(
                    "Owner share: ${contract.ownerSharePercent}%"
                )
                Text(
                    "Cultivator share: " +
                        "${contract.cultivatorSharePercent}%"
                )
                Text("Currency: ${contract.currency}")
            }
        }

        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Contract")
        }

        OutlinedButton(
            onClick = onManageParties,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Parties")
        }

        OutlinedButton(
            onClick = onManageLand,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Land")
        }

        OutlinedButton(
            onClick = onManageCultivation,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cultivation")
        }

        OutlinedButton(
            onClick = onManageExpenses,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Expenses")
        }

        OutlinedButton(
            onClick = onManageHarvest,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Harvest")
        }

        OutlinedButton(
            onClick = onManageEvidence,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Evidence")
        }

        OutlinedButton(
            onClick = onSettlement,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settlement")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
