/**
 * MovieRepository is an interface that defines methods for retrieving movie data from various sources.
 * Implementations of this interface can fetch movie data from local databases, remote APIs, or both.
 */

package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

interface MovieRepository {

    /**
     * Retrieves a list of movies based on a search query.
     *
     * @param title the title of the movies to search for.
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @return a list of movies matching the search criteria.
     */
    suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie>


    /**
     * Retrieves detailed information about a movie based on its IMDb ID.
     *
     * @param imdbId the IMDb ID of the movie to retrieve details for.
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @return detailed information about the movie.
     */
    suspend fun getMovieDetails(imdbId: String, isInternetConnected: Boolean): MovieDetails


    /**
     * Retrieves a list of movies released in a specific year.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param year the year of release of the movies to search for.
     * @return a list of movies matching the search criteria.
     */
    suspend fun getMoviesListByYear(
        title: String = "batman",
        isInternetConnected: Boolean,
        year: String
    ): List<Movie>


    /**
     * Retrieves a list of movies of a specific type.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria.
     */
    suspend fun getMoviesListByType(
        title: String = "batman",
        isInternetConnected: Boolean,
        type: String
    ): List<Movie>


    /**
     * Retrieves a list of movies released in a specific year and of a specific type.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param year the year of release of the movies to search for.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria.
     */
    suspend fun getMoviesListByYearAndType(
        title: String = "batman",
        isInternetConnected: Boolean,
        year: String,
        type: String
    ): List<Movie>

}