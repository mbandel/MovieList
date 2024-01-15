package com.example.movielist.feature.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.movielist.domain.MovieListStatus
import com.example.movielist.domain.usecase.AddFavoriteMovieIdUseCase
import com.example.movielist.domain.usecase.GetFavoriteMovieIdsUseCase
import com.example.movielist.domain.usecase.RemoveFavoriteMovieIdUseCase
import com.example.movielist.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val addFavoriteMovieIdUseCase: AddFavoriteMovieIdUseCase,
    private val removeFavoriteMovieIdUseCase: RemoveFavoriteMovieIdUseCase,
    private val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<NowPlayingState> = MutableStateFlow(NowPlayingState())
    val state: StateFlow<NowPlayingState> = _state

    init {
        getNowPlayingMovies()
    }

    fun addFavouriteMovieId(id: Int) {
        viewModelScope.launch { addFavoriteMovieIdUseCase(id) }
    }

    fun removeFavoriteMovieId(id: Int) {
        viewModelScope.launch { removeFavoriteMovieIdUseCase(id) }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchForMovies()
    }

    private fun searchForMovies() {
        viewModelScope.launch {
            if (state.value.searchQuery != "") {
                searchMovieUseCase(state.value.searchQuery).collect { status ->
                    when (status) {
                        is MovieListStatus.Success -> {
                            _state.update { it.copy(movieInfoList = status.movieInfoViewData) }
                            checkIfFavorite()
                        }

                        MovieListStatus.ConnectionError -> {}
                    }
                }
            } else {
                getNowPlayingMovies()
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collect { status ->
                when (status) {
                    is MovieListStatus.Success -> {
                        _state.update { it.copy(movieInfoList = status.movieInfoViewData) }
                        checkIfFavorite()
                    }
                    MovieListStatus.ConnectionError -> {}
                }
            }
        }
    }

    private suspend fun checkIfFavorite() {
        getFavoriteMovieIdsUseCase().collect { movieIds ->
            for (movie in state.value.movieInfoList) {
                if (movie.id in movieIds) {
                    _state.update {
                        it.copy(movieInfoList = state.value.movieInfoList.map { viewData ->
                            if (viewData.id == movie.id)
                                viewData.copy(isFavorite = true)
                            else viewData
                        })
                    }
                } else {
                    _state.update {
                        it.copy(movieInfoList = state.value.movieInfoList.map { viewData ->
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