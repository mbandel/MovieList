package com.example.movielist.domain

import com.example.movielist.feature.nowplaying.model.MovieInfoViewData

sealed interface MovieListStatus {
    data class Success(val movieInfoViewData: List<MovieInfoViewData>): MovieListStatus
    data object ConnectionError: MovieListStatus
}