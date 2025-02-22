package com.android.news.presentation.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.news.domain.usecases.SaveAppEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.android.news.util.TAG
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveAppEntry: SaveAppEntry
) : ViewModel() {
    fun onEvent(event: OnBoardingEvent) {
        Log.d(TAG, "onEvent: $event")
        when(event) {
            is OnBoardingEvent.SaveAppEntry -> {
                saveAppEntry()
            }

            else -> {

            }
        }
    }

    private fun saveAppEntry() {
        Log.d(TAG, ": ")
        viewModelScope.launch {
            saveAppEntry.saveAppEntry()
        }
    }
}