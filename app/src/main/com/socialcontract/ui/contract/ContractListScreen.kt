package com.socialcontract.ui.contract

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.ContractEntity

@Composable
fun ContractListScreen(
    contracts: List<ContractEntity>,
    isLoading: Boolean,
    onCreateContract: () -> Unit,
    onContractSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contracts",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onCreateContract,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Contract")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (contracts.isEmpty()) {
            Text(
                text = "No contracts yet.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = contracts,
                    key = { it.id }
                ) { contract ->
                    ContractListItem(
                        contract = contract,
                        onClick = {
                            onContractSelected(contract.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ContractListItem(
    contract: ContractEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = contract.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Status: ${contract.status}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Land: ${contract.landArea} ${contract.landAreaUnit}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Share: ${contract.ownerSharePercent}% / " +
                    "${contract.cultivatorSharePercent}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
