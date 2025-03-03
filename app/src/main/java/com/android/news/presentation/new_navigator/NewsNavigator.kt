package com.android.news.presentation.new_navigator

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.news.R
import com.android.news.domain.model.Article
import com.android.news.presentation.bookmark.BookmarkScreen
import com.android.news.presentation.bookmark.BookmarkViewModel
import com.android.news.presentation.details.DetailsScreen
import com.android.news.presentation.details.DetailsViewModel
import com.android.news.presentation.home.HomeScreen
import com.android.news.presentation.home.HomeViewModel
import com.android.news.presentation.navgraph.Route
import com.android.news.presentation.new_navigator.components.BottomNavigationItem
import com.android.news.presentation.new_navigator.components.NewsBottomNavigation
import com.android.news.presentation.search.SearchScreen
import com.android.news.presentation.search.SearchViewModel
import com.android.news.util.UIComponents
import com.google.gson.Gson

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible)
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToScreen(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToScreen(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToScreen(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    })
        })
    { padding ->
        val bottomPadding = padding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
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
                OnBackClickStateSaver(navController = navController)

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
                arguments = listOf(navArgument("article") { type = NavType.StringType })
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

            composable(
                route = Route.BookmarkScreen.route
            ) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                OnBackClickStateSaver(navController = navController)

                BookmarkScreen(
                    state = state.value,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    })
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