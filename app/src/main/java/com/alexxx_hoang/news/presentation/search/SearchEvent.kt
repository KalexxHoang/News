package com.alexxx_hoang.news.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()
    data object SearchNews : SearchEvent()
}