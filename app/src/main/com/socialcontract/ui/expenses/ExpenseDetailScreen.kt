package com.socialcontract.ui.expenses

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
import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.util.CurrencyFormatter
import com.socialcontract.util.DateFormatter

@Composable
fun ExpenseDetailScreen(
    expense: ExpenseEntity,
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
            text = "Expense Details",
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
                    text = expense.description,
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Category: ${expense.category}")

                Text(
                    "Amount: ${
                        CurrencyFormatter.format(
                            expense.amount,
                            expense.currency
                        )
                    }"
                )

                expense.quantity?.let {
                    Text(
                        "Quantity: $it " +
                            (expense.quantityUnit ?: "")
                    )
                }

                expense.payerPartyId?.let {
                    Text("Payer party: $it")
                }

                Text(
                    "Date: ${
                        DateFormatter.format(
                            expense.expenseDate
                        )
                    }"
                )

                expense.receiptReference?.let {
                    Text("Receipt: $it")
                }

                expense.notes?.let {
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
