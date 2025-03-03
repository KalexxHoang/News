package com.alexxx_hoang.news.domain.usecases.news

import com.alexxx_hoang.news.domain.model.Article
import com.alexxx_hoang.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsList @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.getNewsList()
    }
}