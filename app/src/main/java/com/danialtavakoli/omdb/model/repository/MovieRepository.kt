package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

interface MovieRepository {
    suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie>

    suspend fun getMovieDetails(imdbId: String, isInternetConnected: Boolean): MovieDetails
}