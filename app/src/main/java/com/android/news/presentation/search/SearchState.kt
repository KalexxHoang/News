package com.android.news.presentation.search

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)
