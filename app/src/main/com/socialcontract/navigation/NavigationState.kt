package com.socialcontract.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class NavigationState(
    val currentRoute: String = Routes.HOME,
    val canNavigateBack: Boolean = false
)

@Composable
fun rememberNavigationState(
    initialRoute: String = Routes.HOME
): NavigationStateHolder {
    return remember {
        NavigationStateHolder(initialRoute)
    }
}

class NavigationStateHolder(
    initialRoute: String
) {
    var state by mutableStateOf(
        NavigationState(
            currentRoute = initialRoute,
            canNavigateBack = false
        )
    )
        private set

    fun updateRoute(
        route: String,
        canNavigateBack: Boolean
    ) {
        state = NavigationState(
            currentRoute = route,
            canNavigateBack = canNavigateBack
        )
    }

    fun reset() {
        state = NavigationState()
    }
}
