package com.example.movielist.domain

import com.example.movielist.feature.nowplaying.model.NowPlayingViewData

sealed interface NowPlayingStatus {
    data class Success(val nowPlayingViewDataList: List<NowPlayingViewData>): NowPlayingStatus
    data object ConnectionError: NowPlayingStatus
}