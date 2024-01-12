package com.example.movielist.domain

import com.example.movielist.apiservice.ApiService
import com.example.movielist.remote.RecentMoviesRemote
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val apiService: ApiService
): suspend () -> Boolean {
    override suspend fun invoke(): Boolean {
        val request = apiService.getNowPlaying()
        return if (request.isSuccessful) {
            println("REQUEST IS SUCCESSFULL")
            println("movies: ${request.body()}")
            true
        } else {
            false
        }
    }
}