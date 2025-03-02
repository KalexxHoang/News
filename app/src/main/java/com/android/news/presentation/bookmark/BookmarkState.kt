package com.android.news.presentation.bookmark

import com.android.news.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)