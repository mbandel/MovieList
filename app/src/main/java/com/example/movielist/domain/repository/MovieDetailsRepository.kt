package com.example.movielist.domain.repository

import com.example.movielist.apiservice.ApiService
import com.example.movielist.domain.MovieDetailsStatus
import com.example.movielist.feature.details.MovieDetailsViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsStatus> = flow {
        val request = apiService.getMovieDetails(movieId)
        if (request.isSuccessful) {
            val movieDetailsRemote = request.body()
            if (movieDetailsRemote != null) {
                emit(
                    MovieDetailsStatus.Success(
                        movieDetails = MovieDetailsViewData(
                            id = movieDetailsRemote.id,
                            backdropPath = movieDetailsRemote.backdropPath,
                            title = movieDetailsRemote.originalTitle,
                            overview = movieDetailsRemote.overview,
                            releaseDate = movieDetailsRemote.releaseDate,
                            voteAverage = String.format("%.2f", movieDetailsRemote.voteAverage)
                        )
                    )
                )

            }
        } else {
            emit(MovieDetailsStatus.ConnectionError)
        }
    }
}