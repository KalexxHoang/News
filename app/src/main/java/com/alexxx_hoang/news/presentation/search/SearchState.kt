package com.alexxx_hoang.news.presentation.search

import androidx.paging.PagingData
import com.alexxx_hoang.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)
