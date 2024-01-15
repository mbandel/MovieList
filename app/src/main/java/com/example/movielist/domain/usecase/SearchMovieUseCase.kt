package com.example.movielist.domain.usecase

import com.example.movielist.domain.MovieListStatus
import com.example.movielist.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : (String) -> Flow<MovieListStatus> {
    override fun invoke(query: String): Flow<MovieListStatus> {
        return moviesRepository.getSearchedMovies(query)
    }
}