package com.socialcontract.ui.parties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.PartyEntity

@Composable
fun PartyScreen(
    parties: List<PartyEntity>,
    onAddParty: (
        name: String,
        phoneNumber: String?,
        address: String?,
        role: String,
        nationalIdReference: String?
    ) -> Unit,
    onDeleteParty: (PartyEntity) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var nationalId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Parties")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = role,
            onValueChange = { role = it },
            label = { Text("Role") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nationalId,
            onValueChange = { nationalId = it },
            label = { Text("National ID reference") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (name.isBlank() || role.isBlank()) return@Button

                onAddParty(
                    name.trim(),
                    phone.trim().ifBlank { null },
                    address.trim().ifBlank { null },
                    role.trim(),
                    nationalId.trim().ifBlank { null }
                )

                name = ""
                phone = ""
                address = ""
                role = ""
                nationalId = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Party")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = parties,
                key = { it.id }
            ) { party ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(party.name)
                        Text("Role: ${party.role}")

                        party.phoneNumber?.let {
                            Text("Phone: $it")
                        }

                        party.address?.let {
                            Text("Address: $it")
                        }

                        Button(
                            onClick = {
                                onDeleteParty(party)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
