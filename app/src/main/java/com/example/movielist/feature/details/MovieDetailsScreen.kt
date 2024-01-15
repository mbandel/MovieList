package com.example.movielist.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movielist.R
import com.example.movielist.apiservice.ApiConst.BACKDROP_URL

data class MovieDetailsScreen(val movieId: Int) : Screen {
    @Composable
    override fun Content() {
        MovieDetailsComposableScreen(movieId = movieId, viewModel = getViewModel())
    }
}

@Composable
private fun MovieDetailsComposableScreen(movieId: Int, viewModel: MovieDetailsViewModel) {
    LaunchedEffect(true) { viewModel.getMovieDetails(movieId) }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier
            .padding(all = 32.dp)
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold, text = state.movieDetailsViewData.title,
                modifier = Modifier.fillMaxWidth(.7f)
            )
            if (state.movieDetailsViewData.isFavorite) {
                Image(
                    painter = painterResource(id = R.drawable.favorite_star),
                    contentDescription = null,
                    modifier = Modifier.clickable { viewModel.removeFavoriteMovieId(movieId) }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.clickable { viewModel.addFavouriteMovieId(movieId) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        PosterImage(backdropPath = state.movieDetailsViewData.backdropPath)

        Spacer(modifier = Modifier.height(42.dp))

        Text(text = state.movieDetailsViewData.overview)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "${stringResource(id = R.string.release_date)}  ${state.movieDetailsViewData.releaseDate}")

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "${stringResource(id = R.string.average_vote)}  ${state.movieDetailsViewData.voteAverage}")

    }
}


@Composable
fun PosterImage(backdropPath: String) {
    val imageUrl = "$BACKDROP_URL$backdropPath"
    val request = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .build()

    val painter = rememberAsyncImagePainter(request)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.size(width = 400.dp, height = 200.dp)
    )
}