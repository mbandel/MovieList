package com.example.movielist.feature.nowplaying

import com.example.movielist.feature.nowplaying.model.NowPlayingViewData
import com.example.movielist.remote.RecentMoviesRemote

data class NowPlayingState(
    val nowPlayingMovieList: List<NowPlayingViewData> = emptyList()
)
