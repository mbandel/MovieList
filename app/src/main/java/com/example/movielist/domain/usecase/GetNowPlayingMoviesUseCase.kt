package com.example.movielist.domain.usecase

import com.example.movielist.domain.MovieListStatus
import com.example.movielist.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : () -> Flow<MovieListStatus> {
    override fun invoke(): Flow<MovieListStatus> {
        return moviesRepository.getNowPlayingMovies()
    }
}