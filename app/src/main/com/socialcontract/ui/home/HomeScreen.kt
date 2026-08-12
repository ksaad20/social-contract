package com.socialcontract.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCreateContract: () -> Unit,
    onViewContracts: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Social Contract",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Manage land-share cultivation contracts, expenses, harvests, evidence, and settlements.",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCreateContract,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Contract")
        }

        OutlinedButton(
            onClick = onViewContracts,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Contracts")
        }
    }
}
