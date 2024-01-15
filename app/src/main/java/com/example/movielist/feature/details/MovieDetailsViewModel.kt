package com.example.movielist.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.MovieDetailsStatus
import com.example.movielist.domain.usecase.AddFavoriteMovieIdUseCase
import com.example.movielist.domain.usecase.GetFavoriteMovieIdsUseCase
import com.example.movielist.domain.usecase.GetMovieDetailsUseCase
import com.example.movielist.domain.usecase.RemoveFavoriteMovieIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addFavoriteMovieIdUseCase: AddFavoriteMovieIdUseCase,
    private val removeFavoriteMovieIdUseCase: RemoveFavoriteMovieIdUseCase,
    private val getFavoriteMovieIds: GetFavoriteMovieIdsUseCase
): ViewModel() {

    private val _state: MutableStateFlow<MovieDetailsState> = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state


    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect { status ->
                when(status) {
                    is MovieDetailsStatus.Success -> {
                        _state.update { it.copy(movieDetailsViewData = status.movieDetails) }
                        checkIfFavorite()
                    }
                    MovieDetailsStatus.ConnectionError -> { _state.update { it.copy(isConnectionError = true) }}
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
            if (_state.value.movieDetailsViewData.id in movieIds) {
                _state.update {
                    it.copy(
                        movieDetailsViewData = _state.value.movieDetailsViewData.copy(
                            isFavorite = true
                        )
                    )
                }
            }
            else {
                _state.update {
                    it.copy(
                        movieDetailsViewData = _state.value.movieDetailsViewData.copy(
                            isFavorite = false
                        )
                    )
                }

            }
        }
    }
}