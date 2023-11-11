package com.oswaldo.openpay.ui.movies.data.mapper

import com.oswaldo.openpay.core.util.Constants
import com.oswaldo.openpay.ui.movies.data.dto.DataMovies
import com.oswaldo.openpay.ui.movies.domain.model.Movie

fun DataMovies.Result.toDomainEntity() = Movie(
    adult,
    backdropPath = Constants.THUMB_MOVIE_POSTER + backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath = Constants.THUMB_MOVIE_POSTER + posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun Movie.toDto() = DataMovies.Result(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)