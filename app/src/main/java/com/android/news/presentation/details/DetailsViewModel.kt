package com.android.news.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.news.domain.model.Article
import com.android.news.domain.usecases.news.DeleteNews
import com.android.news.domain.usecases.news.GetNews
import com.android.news.domain.usecases.news.InsertNews
import com.android.news.util.UIComponents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getNews: GetNews,
    private val insertNews: InsertNews,
    private val deleteNews: DeleteNews
) : ViewModel() {
    private val _effect = MutableSharedFlow<UIComponents>()
    val effect: SharedFlow<UIComponents>
        get() = _effect.asSharedFlow()

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.InsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = getNews(url = event.article.url)
                    if (article == null){
                        insertArticle(article = event.article)
                    }else{
                        deleteArticle(article = event.article)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sendEffect(UIComponents.None())
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        Log.d("DetailsViewModel", "Delete: $article")
        deleteNews(article = article)
        sendEffect(UIComponents.Toast("Article Deleted"))
    }

    private suspend fun insertArticle(article: Article) {
        Log.d("DetailsViewModel", "Insert: $article")
        insertNews(article = article)
        sendEffect(UIComponents.Toast("Article Inserted"))
    }

    private fun sendEffect(effect: UIComponents) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}