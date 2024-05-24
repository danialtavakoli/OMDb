package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

/**
 * MovieRepository is an interface that defines methods for fetching movie data.
 *
 * This repository abstracts the data source, whether it's from a local database
 * or a remote API, and provides a unified interface for accessing movie data.
 */
interface MovieRepository {
    /**
     * Retrieves a list of movies that match the specified title, year, and type.
     *
     * This method checks if the device is connected to the internet and fetches
     * the data accordingly, either from a local database or a remote API.
     *
     * @param isInternetConnected A boolean indicating if the device is connected to the internet.
     * @param title The title of the movies to search for. This is a required parameter.
     * @param year The year of the movies to search for. This is an optional parameter.
     * @param type The type of the movies to search for. This is an optional parameter.
     * @return A list of movies that match the search criteria.
     */
    suspend fun getMoviesList(
        isInternetConnected: Boolean,
        title: String,
        year: String? = null,
        type: String? = null
    ): List<Movie>


    /**
     * Retrieves the details of a movie by its IMDb ID.
     *
     * This method checks if the device is connected to the internet and fetches
     * the data accordingly, either from a local database or a remote API.
     *
     * @param isInternetConnected A boolean indicating if the device is connected to the internet.
     * @param imdbId The IMDb ID of the movie to retrieve details for. This is a required parameter.
     * @return The details of the movie with the specified IMDb ID.
     */
    suspend fun getMovieDetails(isInternetConnected: Boolean, imdbId: String): MovieDetails
}