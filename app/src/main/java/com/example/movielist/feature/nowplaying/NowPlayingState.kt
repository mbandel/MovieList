package com.example.movielist.feature.nowplaying

import com.example.movielist.feature.nowplaying.model.MovieInfoViewData

data class NowPlayingState(
    val movieInfoList: List<MovieInfoViewData> = emptyList(),
    val searchQuery: String = "",
    val isConnectionError: Boolean = false
)
