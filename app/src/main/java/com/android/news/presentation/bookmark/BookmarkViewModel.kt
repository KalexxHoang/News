package com.android.news.presentation.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.news.domain.usecases.news.GetNewsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getNewsList: GetNewsList
): ViewModel() {
    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState>
        get() = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        getNewsList().onEach { articles ->
            Log.d("BookmarkViewModel", "Bookmark articles: $articles")
            _state.value = _state.value.copy(
                articles = articles
            )
        }.launchIn(viewModelScope)
    }
}