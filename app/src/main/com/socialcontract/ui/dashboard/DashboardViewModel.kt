package com.socialcontract.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.data.database.entities.HarvestEntity
import com.socialcontract.data.repository.ContractRepository
import com.socialcontract.data.repository.ExpenseRepository
import com.socialcontract.data.repository.HarvestRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val contractRepository: ContractRepository,
    private val expenseRepository: ExpenseRepository,
    private val harvestRepository: HarvestRepository
) : ViewModel() {

    val dashboard: StateFlow<DashboardUiModel> =
        combine(
            contractRepository.observeAllContracts(),
            expenseRepository.observeAllExpenses(),
            harvestRepository.observeAllHarvests()
        ) { contracts, expenses, harvests ->
            buildDashboard(
                contracts = contracts,
                expenses = expenses,
                harvests = harvests
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiModel(
                contractCount = 0,
                activeContractCount = 0,
                totalLandArea = 0.0,
                totalLandAreaUnit = "m²",
                totalExpenses = 0.0,
                expenseCurrency = "BDT",
                totalHarvestQuantity = 0.0,
                harvestUnit = "kg",
                totalHarvestValue = 0.0,
                harvestCurrency = "BDT"
            )
        )

    private fun buildDashboard(
        contracts: List<ContractEntity>,
        expenses: List<ExpenseEntity>,
        harvests: List<HarvestEntity>
    ): DashboardUiModel {
        val landUnit =
            contracts.firstOrNull()?.landAreaUnit ?: "m²"

        val expenseCurrency =
            expenses.firstOrNull()?.currency ?: "BDT"

        val harvestUnit =
            harvests.firstOrNull()?.unit ?: "kg"

        val harvestCurrency =
            harvests.firstOrNull()?.currency ?: "BDT"

        return DashboardUiModel(
            contractCount = contracts.size,
            activeContractCount = contracts.count {
                it.status == "ACTIVE"
            },
            totalLandArea = contracts
                .filter {
                    it.landAreaUnit == landUnit
                }
                .sumOf { it.landArea },
            totalLandAreaUnit = landUnit,
            totalExpenses = expenses
                .filter {
                    it.currency == expenseCurrency
                }
                .sumOf { it.amount },
            expenseCurrency = expenseCurrency,
            totalHarvestQuantity = harvests
                .filter {
                    it.unit == harvestUnit
                }
                .sumOf { it.quantity },
            harvestUnit = harvestUnit,
            totalHarvestValue = harvests
                .filter {
                    it.currency == harvestCurrency
                }
                .sumOf { it.totalValue },
            harvestCurrency = harvestCurrency
        )
    }
}
