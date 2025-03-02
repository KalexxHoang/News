package com.android.news.domain.repository

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // Remote Source
    fun getRemoteNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchRemoteNews(sources: List<String>, searchQuery: String): Flow<PagingData<Article>>

    // Local Source
    fun getNewsList(): Flow<List<Article>>
    suspend fun getNews(url: String): Article?
    suspend fun insertNews(article: Article)
    suspend fun deleteNews(article: Article)
}