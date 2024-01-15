package com.example.movielist.domain.usecase

import com.example.movielist.database.MovieListDatabase
import com.example.movielist.database.dao.FavoriteMovieIdsDao
import com.example.movielist.database.entity.FavoriteMovieIdsEntity
import javax.inject.Inject

class RemoveFavoriteMovieIdUseCase @Inject constructor(
    private val favoriteMovieIdsDao: FavoriteMovieIdsDao
): suspend (Int) -> Unit {
    override suspend fun invoke(id: Int) {
        favoriteMovieIdsDao.removeMovieId(FavoriteMovieIdsEntity(id))
    }
}