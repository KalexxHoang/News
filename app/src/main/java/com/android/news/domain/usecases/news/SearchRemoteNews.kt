package com.android.news.domain.usecases.news

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import com.android.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRemoteNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>, searchQuery: String): Flow<PagingData<Article>> {
        return newsRepository.searchRemoteNews(sources = sources, searchQuery = searchQuery)
    }
}