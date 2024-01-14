package com.example.movielist.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.MovieDetailsStatus
import com.example.movielist.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): ViewModel() {

    private val _state: MutableStateFlow<MovieDetailsState> = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state



    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect { status ->
                when(status) {
                    is MovieDetailsStatus.Success -> _state.update { it.copy(movieDetailsViewData = status.movieDetails) }
                    MovieDetailsStatus.ConnectionError -> { _state.update { it.copy(isConnectionError = true) }}
                }

            }
        }
    }
}