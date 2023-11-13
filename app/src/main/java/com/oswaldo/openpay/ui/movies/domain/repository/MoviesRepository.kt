package com.oswaldo.openpay.ui.movies.domain.repository

import com.oswaldo.openpay.ui.movies.domain.model.Movie


interface MoviesRepository {
    suspend fun getPopularMovies() : List<Movie>
    suspend fun getTopRatedMovies() : List<Movie>
    suspend fun getUpcomingMovies() : List<Movie>
}