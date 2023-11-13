package com.oswaldo.openpay.ui.movies.domain.usecase

import com.oswaldo.openpay.ui.movies.domain.model.Movie
import com.oswaldo.openpay.ui.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke() : List<Movie> {
        return withContext(Dispatchers.IO) {
            moviesRepository.getUpcomingMovies()
        }
    }
}