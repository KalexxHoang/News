package com.android.news.domain.repository

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(sources: List<String>, searchQuery: String): Flow<PagingData<Article>>
}