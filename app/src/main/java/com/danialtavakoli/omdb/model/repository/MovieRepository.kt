package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie

interface MovieRepository {
    suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie>

}