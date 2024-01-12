package com.example.movielist.apiservice

import com.example.movielist.remote.RecentMoviesRemote
import retrofit2.Response

import retrofit2.http.GET

interface ApiService {

    @GET(ApiConst.NOW_PLAYING)
    suspend fun getNowPlaying(): Response<RecentMoviesRemote>
}