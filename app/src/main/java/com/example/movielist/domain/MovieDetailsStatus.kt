package com.example.movielist.domain

import com.example.movielist.feature.details.MovieDetailsViewData

sealed interface MovieDetailsStatus {
    data class Success(val movieDetails: MovieDetailsViewData): MovieDetailsStatus
    data object ConnectionError: MovieDetailsStatus
}