package com.android.news.domain.usecases.news

import com.android.news.domain.model.Article
import com.android.news.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.deleteNews(article)
    }
}