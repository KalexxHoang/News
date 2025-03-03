package com.alexxx_hoang.news.presentation.bookmark

import com.alexxx_hoang.news.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)