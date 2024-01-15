package com.example.movielist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movielist.apiservice.ApiConst
import com.example.movielist.apiservice.ApiService
import com.example.movielist.apiservice.HeaderInterceptor
import com.example.movielist.database.DatabaseConst.DATABASE_NAME
import com.example.movielist.database.MovieListDatabase
import com.example.movielist.database.dao.FavoriteMovieIdsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//interface BindModule {
//
//}

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun provideServiceApi(): ApiService {
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConst.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .build()
            )
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieListDatabase {
        return Room.databaseBuilder(
            context,
            MovieListDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesFavoriteMovieIdsDao(database: MovieListDatabase): FavoriteMovieIdsDao {
        return database.favoriteMovieIdsDao()
    }
}