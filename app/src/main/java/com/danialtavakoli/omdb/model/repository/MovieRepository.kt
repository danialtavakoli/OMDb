package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

interface MovieRepository {
    suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie>

    suspend fun getMovieDetails(imdbId: String, isInternetConnected: Boolean): MovieDetails

    suspend fun getMoviesListByYear(
        title: String = "batman",
        isInternetConnected: Boolean,
        year: String
    ): List<Movie>

    suspend fun getMoviesListByType(
        title: String = "batman",
        isInternetConnected: Boolean,
        type: String
    ): List<Movie>

    suspend fun getMoviesListByYearAndType(
        title: String = "batman",
        isInternetConnected: Boolean,
        year: String,
        type: String
    ): List<Movie>

}