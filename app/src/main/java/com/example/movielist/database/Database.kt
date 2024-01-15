package com.example.movielist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.database.dao.FavoriteMovieIdsDao
import com.example.movielist.database.entity.FavoriteMovieIdsEntity

@Database(
    entities = [FavoriteMovieIdsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieListDatabase : RoomDatabase() {
    abstract fun favoriteMovieIdsDao(): FavoriteMovieIdsDao
}