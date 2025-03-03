package com.alexxx_hoang.news.presentation.details

import com.alexxx_hoang.news.domain.model.Article

sealed class DetailsEvent {
    data class InsertDeleteArticle(val article: Article) : DetailsEvent()
    data object RemoveSideEffect : DetailsEvent()
}