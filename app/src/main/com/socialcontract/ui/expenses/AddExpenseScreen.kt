package com.socialcontract.ui.expenses

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.util.CurrencyFormatter
import com.socialcontract.util.DateFormatter

@Composable
fun ExpenseLedgerScreen(
    expenses: List<ExpenseEntity>,
    onAddExpense: () -> Unit,
    onExpenseSelected: (String) -> Unit
) {
    val total = expenses.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Expense Ledger",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Total: ${
                CurrencyFormatter.format(
                    total,
                    expenses.firstOrNull()?.currency ?: "BDT"
                )
            }",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onAddExpense,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Expense")
        }

        if (expenses.isEmpty()) {
            Text("No expenses recorded yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = expenses,
                    key = { it.id }
                ) { expense ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onExpenseSelected(expense.id)
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = expense.description,
                                style = MaterialTheme.typography.titleMedium
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

                            expense.quantity?.let { quantity ->
                                Text(
                                    "Quantity: $quantity " +
                                        (expense.quantityUnit ?: "")
                                )
                            }

                            Text(
                                "Date: ${
                                    DateFormatter.format(
                                        expense.expenseDate
                                    )
                                }"
                            )
                        }
                    }
                }
            }
        }
    }
}
