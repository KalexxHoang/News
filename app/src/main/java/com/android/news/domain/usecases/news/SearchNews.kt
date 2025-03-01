package com.android.news.domain.usecases.news

import androidx.paging.PagingData
import com.android.news.domain.model.Article
import com.android.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>, searchQuery: String): Flow<PagingData<Article>> {
        return newsRepository.searchNews(sources = sources, searchQuery = searchQuery)
    }
}