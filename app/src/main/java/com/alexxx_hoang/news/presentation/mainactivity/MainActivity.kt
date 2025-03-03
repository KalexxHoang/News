package com.alexxx_hoang.news.presentation.mainactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.alexxx_hoang.news.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint
import com.alexxx_hoang.news.presentation.navgraph.NavGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.splashCondition.value
        }

        setContent {
            NewsTheme(
                dynamicColor = false
            ) {
                val isDarkMode = isSystemInDarkTheme()
                val systemUI = rememberSystemUiController()
                SideEffect {
                    systemUI.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isDarkMode
                    )
                }

                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    val startDestination = viewModel.startDestination.value
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}