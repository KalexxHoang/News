package com.android.news.presentation.new_navigator

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.news.domain.model.Article
import com.android.news.presentation.details.DetailsScreen
import com.android.news.presentation.details.DetailsViewModel
import com.android.news.presentation.home.HomeScreen
import com.android.news.presentation.home.HomeViewModel
import com.android.news.presentation.navgraph.Route
import com.android.news.presentation.search.SearchScreen
import com.android.news.presentation.search.SearchViewModel
import com.android.news.util.UIComponents
import com.google.gson.Gson

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
                navigateToDetails = { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
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
                navigateToDetails = { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
            })
        }

        composable(
            route = Route.DetailsScreen.route + "?article={article}",
            arguments = listOf(navArgument("article") {type = NavType.StringType})
        ) { backstackEntry ->
            val json = backstackEntry.arguments?.getString("article")
            Log.i("NewsNavigator", "DetailsScreen: Article json___$json")
            val article = Gson().fromJson(json, Article::class.java)

            article?.let {
                val viewModel: DetailsViewModel = hiltViewModel()
                val effect = viewModel.effect.collectAsState(initial = UIComponents.None())

                DetailsScreen(
                    article = article,
                    event = viewModel::onEvent,
                    navigateUp = { navController.navigateUp() },
                    sideEffect = effect.value
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToScreen(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

private fun navigateToScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen ->
            popUpTo(screen) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    val json = Gson().toJson(article)
    Log.d("DetailsScreen", "Article: $json")
    navController.navigate(
        route = Route.DetailsScreen.route + "?article=$json"
    )
}