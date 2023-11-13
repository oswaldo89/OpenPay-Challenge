package com.oswaldo.openpay.ui.movies.presentation

import android.util.Log
import com.oswaldo.openpay.BaseViewModel
import com.oswaldo.openpay.core.di.IoDispatcher
import com.oswaldo.openpay.core.util.launch
import com.oswaldo.openpay.ui.movies.domain.model.Movie
import com.oswaldo.openpay.ui.movies.domain.usecase.GetPopularMoviesUseCase
import com.oswaldo.openpay.ui.movies.domain.usecase.GetRatedMoviesUseCase
import com.oswaldo.openpay.ui.movies.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val ratedMoviesUseCase: GetRatedMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    @IoDispatcher private var dispatcher: CoroutineDispatcher
) : BaseViewModel<MoviesViewModel.State, MoviesViewModel.Navigation>() {
    private lateinit var popularMoviesList: List<Movie>
    private lateinit var ratedMoviesList: List<Movie>
    private lateinit var upcomingMoviesList: List<Movie>

    fun init() {
        launch(
            onError = {
                Log.v("MoviesViewModel", "error: ${it.message}")
            },
            block = {
                popularMoviesList = popularMoviesUseCase()
                ratedMoviesList = ratedMoviesUseCase()
                upcomingMoviesList = upcomingMoviesUseCase()
                setState(State.ShowData(popularMoviesList, ratedMoviesList, upcomingMoviesList))
            },
            dispatcher = dispatcher
        )
    }

    sealed class State {
        class ShowData(val popularMovies: List<Movie>, val topRelatedMovies: List<Movie>, val upcomingMovies: List<Movie>) : State()
    }

    sealed class Navigation {
        class ChipValue(val type: String, val value: String) : Navigation()
    }

    companion object {
        const val NUMBER_OF_RECOMMENDED_MOVIES = 6
    }
}
