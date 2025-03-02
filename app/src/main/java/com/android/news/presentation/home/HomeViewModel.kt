package com.android.news.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.news.domain.model.Article
import com.android.news.domain.usecases.news.GetRemoteNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetRemoteNews
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state

    val news: Flow<PagingData<Article>> = getNewsUseCase(
        sources = listOf("bbc-news","abc-news","al-jazeera-english")
    ).cachedIn(viewModelScope)

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }

    private fun updateScrollValue(newValue: Int){
        _state.value = _state.value.copy(scrollValue = newValue)
    }
    private fun updateMaxScrollingValue(newValue: Int){
        _state.value = _state.value.copy(maxScrollingValue = newValue)
    }
}