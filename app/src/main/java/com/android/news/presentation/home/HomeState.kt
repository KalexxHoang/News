package com.android.news.presentation.home

import com.android.news.domain.model.Article

data class HomeState (
    val newsTicker: String = "",
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0
)