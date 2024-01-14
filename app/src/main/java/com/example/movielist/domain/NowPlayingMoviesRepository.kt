package com.example.movielist.domain

import com.example.movielist.apiservice.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingMoviesRepository @Inject constructor(
    private val apiService: ApiService
) {
}