package com.example.movielist.feature.details

data class MovieDetailsState(
    val movieDetailsViewData: MovieDetailsViewData = MovieDetailsViewData(),
    val isConnectionError: Boolean = false
)
