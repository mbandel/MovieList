package com.example.movielist.feature.details

data class MovieDetailsViewData(
    val posterPath: String = "",
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val voteAverage: String = "",
    val isFavorite: Boolean = false
)
