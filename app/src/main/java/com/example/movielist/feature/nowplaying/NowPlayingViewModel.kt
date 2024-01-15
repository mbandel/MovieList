package com.example.movielist.feature.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.movielist.domain.NowPlayingStatus
import com.example.movielist.domain.usecase.AddFavoriteMovieIdUseCase
import com.example.movielist.domain.usecase.GetFavoriteMovieIds
import com.example.movielist.domain.usecase.RemoveFavoriteMovieIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val addFavoriteMovieIdUseCase: AddFavoriteMovieIdUseCase,
    private val removeFavoriteMovieIdUseCase: RemoveFavoriteMovieIdUseCase,
    private val getFavoriteMovieIds: GetFavoriteMovieIds
) : ViewModel() {

    private val _state: MutableStateFlow<NowPlayingState> = MutableStateFlow(NowPlayingState())
    val state: StateFlow<NowPlayingState> = _state

    init {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collect { status ->
                when (status) {
                    is NowPlayingStatus.Success -> {
                        _state.update { it.copy(nowPlayingMovieList = status.nowPlayingViewDataList) }
                        checkIfFavorite()
                    }
                    NowPlayingStatus.ConnectionError -> {}
                }
            }
        }
    }

    fun addFavouriteMovieId(id: Int) {
        viewModelScope.launch { addFavoriteMovieIdUseCase(id) }
    }

    fun removeFavoriteMovieId(id: Int) {
        viewModelScope.launch { removeFavoriteMovieIdUseCase(id) }
    }

    private suspend fun checkIfFavorite() {
        getFavoriteMovieIds().collect { movieIds ->
            for (movie in state.value.nowPlayingMovieList) {
                if (movie.id in movieIds) {
                    _state.update {
                        it.copy(nowPlayingMovieList = state.value.nowPlayingMovieList.map { viewData ->
                            if (viewData.id == movie.id)
                                viewData.copy(isFavorite = true)
                            else viewData
                        })
                    }
                } else {
                    _state.update {
                        it.copy(nowPlayingMovieList = state.value.nowPlayingMovieList.map { viewData ->
                            if (viewData.id == movie.id)
                                viewData.copy(isFavorite = false)
                            else viewData
                        })
                    }
                }
            }
        }
    }
}