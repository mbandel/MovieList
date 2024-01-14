package com.example.movielist.feature.nowplaying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.movielist.feature.details.MovieDetailsScreen

data class NowPlayingScreen(val viewModel: NowPlayingViewModel): Screen {
    @Composable
    override fun Content() {
        NowPlayingMoviesComposableScreen(viewModel = viewModel)
    }
}

@Composable
private fun NowPlayingMoviesComposableScreen(
    viewModel: NowPlayingViewModel
) {
    val state = viewModel.state.collectAsState()
    val movieList = state.value.nowPlayingMovieList
    val navigator = LocalNavigator.currentOrThrow

    Spacer(modifier = Modifier.height(24.dp))
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movieList) { movie ->
            MovieItem(movieName = movie.name,
                isFavorite = movie.isFavorite,
                onItemClicked = {
                    navigator.push(MovieDetailsScreen(movieId = movie.id))
                }
            )
        }
    }
}

@Composable
private fun MovieItem(movieName: String, isFavorite: Boolean, onItemClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(.85f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movieName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onItemClicked() }
        )
        if (isFavorite)
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow
            )
        else
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Black
            )
    }
}