package com.example.movielist.feature.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

data class MovieDetailsScreen(val movieId: Int): Screen {
    @Composable
    override fun Content() {
        MovieDetailsComposableScreen(movieId = movieId)
    }
}
@Composable
private fun MovieDetailsComposableScreen(movieId: Int) {
    Text("movie id = $movieId")
}