package com.socialcontract.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.socialcontract.ui.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Routes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onCreateContract = {
                    navController.navigate(Routes.CREATE_CONTRACT)
                },
                onViewContracts = {
                    navController.navigate(Routes.CONTRACT_LIST)
                }
            )
        }

        composable(Routes.CONTRACT_LIST) {
            // ContractListScreen will be connected here.
        }

        composable(Routes.CREATE_CONTRACT) {
            // CreateContractScreen will be connected here.
        }

        composable(Routes.CONTRACT_DETAIL) {
            // ContractDetailScreen will be connected here.
        }
    }
}
