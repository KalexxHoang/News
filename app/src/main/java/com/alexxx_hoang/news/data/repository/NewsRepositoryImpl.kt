package com.alexxx_hoang.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexxx_hoang.news.data.local.NewsDao
import com.alexxx_hoang.news.data.remote.NewsApi
import com.alexxx_hoang.news.data.remote.NewsPagingSource
import com.alexxx_hoang.news.data.remote.SearchNewsPagingSource
import com.alexxx_hoang.news.domain.model.Article
import com.alexxx_hoang.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getRemoteNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(newsApi = newsApi, sources = sources.joinToString(separator = ","))
            }
        ).flow
    }

    override fun searchRemoteNews(sources: List<String>, searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ","),
                    searchQuery = searchQuery
                )
            }
        ).flow
    }

    override fun getNewsList(): Flow<List<Article>> {
        return newsDao.getNews()
    }

    override suspend fun getNews(url: String): Article? {
        return newsDao.getNews(url = url)
    }

    override suspend fun insertNews(article: Article) {
        newsDao.insertNews(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsDao.deleteNews(article)
    }
}