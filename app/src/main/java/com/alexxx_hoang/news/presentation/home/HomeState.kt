package com.alexxx_hoang.news.presentation.home

import com.alexxx_hoang.news.domain.model.Article

data class HomeState (
    val newsTicker: String = "",
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0
)