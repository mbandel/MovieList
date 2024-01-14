package com.example.movielist.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.hilt.getViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.compose.rememberImagePainter

data class MovieDetailsScreen(val movieId: Int): Screen {
    @Composable
    override fun Content() {
        MovieDetailsComposableScreen(movieId = movieId, viewModel = getViewModel())
    }
}
@Composable
private fun MovieDetailsComposableScreen(movieId: Int, viewModel: MovieDetailsViewModel) {
    LaunchedEffect(true) { viewModel.getMovieDetails(movieId) }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        Text(text = state.movieDetailsViewData.posterPath)
        Text(text = state.isConnectionError.toString())
    }
//    PosterImage(posterPath = state.value.movieDetailsViewData.posterPath)
}


@Composable
fun PosterImage(posterPath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"
    val request = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .build()

    val painter = rememberImagePainter(request)

    Image(
        painter = painter,
        contentDescription = null,
    )
}