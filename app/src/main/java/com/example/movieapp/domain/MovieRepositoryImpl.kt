package com.example.movieapp.domain

import com.example.movieapp.data.model.MovieList
import com.example.movieapp.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource): MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
       return dataSource.getUpcomingMovies()
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return dataSource.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): MovieList {
        return dataSource.getPopularMovies()
    }
}