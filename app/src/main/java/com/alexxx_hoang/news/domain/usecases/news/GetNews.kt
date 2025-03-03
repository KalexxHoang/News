package com.alexxx_hoang.news.domain.usecases.news

import com.alexxx_hoang.news.domain.model.Article
import com.alexxx_hoang.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? {
        return newsRepository.getNews(url)
    }
}