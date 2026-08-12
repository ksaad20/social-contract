package com.socialcontract.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.data.repository.ExpenseRepository
import com.socialcontract.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ExpenseUiState(
    val selectedExpense: ExpenseEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val contractId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    val expenses: StateFlow<List<ExpenseEntity>> =
        expenseRepository
            .observeExpenses(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun selectExpense(expenseId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val expense =
                    expenseRepository.getExpense(expenseId)

                _uiState.value = _uiState.value.copy(
                    selectedExpense = expense,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to load expense."
                )
            }
        }
    }

    fun addExpense(
        category: String,
        description: String,
        amount: Double,
        currency: String,
        quantity: Double?,
        quantityUnit: String?,
        payerPartyId: String?,
        expenseDate: Long,
        receiptReference: String?,
        notes: String?,
        onCreated: (String) -> Unit = {}
    ) {
        if (
            category.isBlank() ||
            description.isBlank() ||
            amount < 0.0 ||
            currency.isBlank() ||
            expenseDate <= 0L
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid expense data."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val expenseId = addExpenseUseCase(
                    contractId = contractId,
                    category = category,
                    description = description,
                    amount = amount,
                    currency = currency,
                    quantity = quantity,
                    quantityUnit = quantityUnit,
                    payerPartyId = payerPartyId,
                    expenseDate = expenseDate,
                    receiptReference = receiptReference,
                    notes = notes
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                onCreated(expenseId)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to add expense."
                )
            }
        }
    }

    fun updateExpense(
        expense: ExpenseEntity
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                expenseRepository.updateExpense(
                    expense.copy(
                        updatedAt = System.currentTimeMillis()
                    )
                )

                _uiState.value = _uiState.value.copy(
                    selectedExpense = expense,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to update expense."
                )
            }
        }
    }

    fun deleteExpense(
        expense: ExpenseEntity,
        onDeleted: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                expenseRepository.deleteExpense(expense)

                _uiState.value = ExpenseUiState()

                onDeleted()
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        exception.message
                            ?: "Unable to delete expense."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}
