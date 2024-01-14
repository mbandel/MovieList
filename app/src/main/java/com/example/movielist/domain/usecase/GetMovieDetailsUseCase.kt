package com.example.movielist.domain.usecase

import com.example.movielist.apiservice.ApiService
import com.example.movielist.domain.MovieDetailsStatus
import com.example.movielist.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
): (Int) -> Flow<MovieDetailsStatus> {
    override fun invoke(movieId: Int): Flow<MovieDetailsStatus> {
        return movieDetailsRepository.getMovieDetails(movieId)
    }
}