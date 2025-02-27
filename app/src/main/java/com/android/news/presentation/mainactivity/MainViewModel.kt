package com.android.news.presentation.mainactivity

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.news.domain.usecases.appentry.ReadAppEntry
import com.android.news.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.android.news.util.TAG

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readAppEntry: ReadAppEntry
): ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        readAppEntry().onEach { shouldStartWithHome ->
            if (shouldStartWithHome)
                _startDestination.value = Route.NewsNavigation.route
            else
                _startDestination.value = Route.AppStartNavigation.route
            Log.d(TAG, "Start destination is ${_startDestination.value}")

            delay(300)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}