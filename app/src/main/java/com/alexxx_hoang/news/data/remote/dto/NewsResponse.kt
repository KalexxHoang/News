package com.alexxx_hoang.news.data.remote.dto

import com.alexxx_hoang.news.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)