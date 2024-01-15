package com.example.movielist.domain.repository

import com.example.movielist.apiservice.ApiService
import com.example.movielist.domain.MovieListStatus
import com.example.movielist.feature.nowplaying.model.MovieInfoViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getNowPlayingMovies(): Flow<MovieListStatus> = flow {
        val request = apiService.getNowPlaying()
        if (request.isSuccessful) {
            emit(
                MovieListStatus.Success(
                    request.body()!!.results.map { result ->
                        MovieInfoViewData(
                            id = result.id,
                            name = result.title
                        )
                    })
            )
        }
        else emit(MovieListStatus.ConnectionError)
    }

    fun getSearchedMovies(query: String): Flow<MovieListStatus> = flow {
        val request = apiService.searchMovie(query)
        if (request.isSuccessful) {
            emit(
                MovieListStatus.Success(
                    request.body()!!.results.map { result ->
                        MovieInfoViewData(
                            id = result.id,
                            name = result.title
                        )
                    })
            )
        } else emit(MovieListStatus.ConnectionError)
    }
}