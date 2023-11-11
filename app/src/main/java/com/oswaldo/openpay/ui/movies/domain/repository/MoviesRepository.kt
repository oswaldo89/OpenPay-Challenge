package com.oswaldo.openpay.ui.movies.domain.repository

import com.oswaldo.openpay.ui.movies.domain.model.Movie


interface MoviesRepository {
    suspend fun getUpcomingMovies() : List<Movie>
    suspend fun getTopRatedMovies() : List<Movie>
}