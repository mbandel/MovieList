package com.example.movielist.domain.usecase

import com.example.movielist.database.MovieListDatabase
import com.example.movielist.database.dao.FavoriteMovieIdsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMovieIds @Inject constructor(
    private val favoriteMovieIdsDao: FavoriteMovieIdsDao
) : () -> Flow<List<Int>> {
    override fun invoke(): Flow<List<Int>> {
        return favoriteMovieIdsDao.observeFavoriteMovieIds().map {
           it.map { favoriteMovieIdsEntity -> favoriteMovieIdsEntity.movieId }
        }
    }
}