package com.android.news.presentation.details

import com.android.news.domain.model.Article

sealed class DetailsEvent {
    data class InsertDeleteArticle(val article: Article) : DetailsEvent()
    data object RemoveSideEffect : DetailsEvent()
}