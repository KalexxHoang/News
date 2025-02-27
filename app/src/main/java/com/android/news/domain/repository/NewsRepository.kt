package com.android.news.domain.repository

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: String): Flow<PagingData<Article>>
}