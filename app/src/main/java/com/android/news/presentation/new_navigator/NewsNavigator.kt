package com.android.news.presentation.new_navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.news.presentation.home.HomeScreen
import com.android.news.presentation.home.HomeViewModel
import com.android.news.presentation.navgraph.Route
import com.android.news.presentation.search.SearchScreen
import com.android.news.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Route.HomeScreen.route) {
        composable(
            route = Route.HomeScreen.route
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            val articles = viewModel.news.collectAsLazyPagingItems()
            val state = viewModel.state.collectAsState()

            HomeScreen(
                articles = articles,
                state = state.value,
                event = viewModel::onEvent,
                navigateToSearch = {
                    navigateToScreen(
                        navController = navController,
                        route = Route.SearchScreen.route
                    )
                },
                navigateToDetails = {

                })
        }

        composable(
            route = Route.SearchScreen.route
        ) {
            val viewModel: SearchViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            SearchScreen(
                state = state.value,
                event = viewModel::onEvent,
                navigateToDetails = {

            })
        }

        composable(
            route = Route.DetailsScreen.route
        ) {

        }
    }
}

private fun navigateToScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}