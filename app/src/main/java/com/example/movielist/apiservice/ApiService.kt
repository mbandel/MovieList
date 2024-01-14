package com.example.movielist.apiservice

import com.example.movielist.remote.MovieDetailsRemote
import com.example.movielist.remote.RecentMoviesRemote
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(ApiConst.NOW_PLAYING)
    suspend fun getNowPlaying(): Response<RecentMoviesRemote>

    @GET(ApiConst.DETAILS + "{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<MovieDetailsRemote>
}