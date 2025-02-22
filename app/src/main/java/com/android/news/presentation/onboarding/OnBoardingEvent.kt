package com.android.news.presentation.onboarding

sealed class OnBoardingEvent {
    data object SaveAppEntry: OnBoardingEvent()
}