package com.oswaldo.openpay.ui.movies.presentation.adapters

import com.oswaldo.openpay.ui.movies.domain.model.Movie

interface IMovieEvent {
    fun onClickMovie(movie : Movie)
}