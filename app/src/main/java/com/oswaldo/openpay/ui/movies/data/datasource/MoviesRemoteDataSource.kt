package com.oswaldo.openpay.ui.movies.data.datasource

import com.oswaldo.openpay.ui.movies.data.dto.DataMovies
import retrofit2.http.GET


interface MoviesRemoteDataSource {
    @GET("movie/popular")
    suspend fun getPopularMovies(): DataMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): DataMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): DataMovies
}