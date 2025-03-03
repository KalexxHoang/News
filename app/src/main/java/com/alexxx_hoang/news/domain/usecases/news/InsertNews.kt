package com.alexxx_hoang.news.domain.usecases.news

import com.alexxx_hoang.news.domain.model.Article
import com.alexxx_hoang.news.domain.repository.NewsRepository
import javax.inject.Inject

class InsertNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.insertNews(article)
    }
}