package com.oswaldo.openpay.ui.movies.di
import com.oswaldo.openpay.ui.movies.data.datasource.MoviesRemoteDataSource
import com.oswaldo.openpay.ui.movies.data.repository.DefaultMoviesRepository
import com.oswaldo.openpay.ui.movies.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {
    @Provides
    fun provideDefaultMoviesRepository(apiService: MoviesRemoteDataSource): MoviesRepository = DefaultMoviesRepository(apiService)
}
