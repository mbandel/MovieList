package com.example.movielist.feature.nowplaying

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecentMoviesScreen (
    viewModel: NowPlayingViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Text(text = state.value.isSuccess)
}