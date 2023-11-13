package com.oswaldo.openpay.ui.movies.data.repository

import com.oswaldo.openpay.ui.movies.data.datasource.MoviesRemoteDataSource
import com.oswaldo.openpay.ui.movies.data.mapper.toDomainEntity
import com.oswaldo.openpay.ui.movies.domain.model.Movie
import com.oswaldo.openpay.ui.movies.domain.repository.MoviesRepository


class DefaultMoviesRepository(private val remoteDataSource: MoviesRemoteDataSource) : MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        return remoteDataSource.getPopularMovies().results.map { it.toDomainEntity() }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return remoteDataSource.getTopRatedMovies().results.map { it.toDomainEntity() }
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return remoteDataSource.getUpcomingMovies().results.map { it.toDomainEntity() }
    }
}