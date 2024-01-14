package com.example.movielist.domain

import com.example.movielist.apiservice.ApiService
import com.example.movielist.feature.nowplaying.model.NowPlayingViewData
import com.example.movielist.remote.RecentMoviesRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val apiService: ApiService
) : () -> Flow<NowPlayingStatus> {
    override fun invoke(): Flow<NowPlayingStatus> = flow {
        val request = apiService.getNowPlaying()
        if (request.isSuccessful && request.body() != null) {
            emit(
                NowPlayingStatus.Success(
                    request.body()!!.results.map { result ->
                        NowPlayingViewData(
                            id = result.id,
                            name = result.originalTitle
                        )
                    })
            )
        }
        else emit(NowPlayingStatus.ConnectionError)
    }
}