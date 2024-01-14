package com.example.movielist.feature.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.movielist.domain.NowPlayingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<NowPlayingState> = MutableStateFlow(NowPlayingState())
    val state: StateFlow<NowPlayingState> = _state

    init {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collect { status ->
                when (status) {
                    is NowPlayingStatus.Success -> _state.update { it.copy(nowPlayingMovieList = status.nowPlayingViewDataList) }
                    NowPlayingStatus.ConnectionError -> {}
                }
            }

        }
    }
}