package com.example.movielist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movielist.database.DatabaseConst

@Entity(tableName = DatabaseConst.FAVORITE_IDS_TABLE_NAME)
data class FavoriteMovieIdsEntity(
    @PrimaryKey(autoGenerate = false)
    val movieId: Int
)