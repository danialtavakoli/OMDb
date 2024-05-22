/**
 * ApiService is an interface that defines the endpoints for making network requests to the OMDB API.
 */

package com.danialtavakoli.omdb.model.net

import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.MovieSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Retrieves a list of movies based on a search query.
     *
     * @param apiKey the API key for accessing the OMDB API.
     * @param title the title of the movies to search for.
     * @return a Response containing the search results as a MovieSearch object.
     */
    @GET("/")
    suspend fun getMoviesList(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String,
    ): Response<MovieSearch>


    /**
     * Retrieves detailed information about a movie based on its IMDb ID.
     *
     * @param apiKey the API key for accessing the OMDB API.
     * @param imdbID the IMDb ID of the movie to retrieve details for.
     * @return a Response containing the movie details as a MovieDetails object.
     */
    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("i") imdbID: String,
    ): Response<MovieDetails>


    /**
     * Retrieves a list of movies released in a specific year.
     *
     * @param apiKey the API key for accessing the OMDB API.
     * @param title the title of the movies to search for (default value is "batman").
     * @param year the year of release of the movies to search for.
     * @return a Response containing the search results as a MovieSearch object.
     */
    @GET("/")
    suspend fun getMoviesListByYear(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String = "batman",
        @Query("y") year: String,
    ): Response<MovieSearch>


    /**
     * Retrieves a list of movies of a specific type.
     *
     * @param apiKey the API key for accessing the OMDB API.
     * @param title the title of the movies to search for (default value is "batman").
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a Response containing the search results as a MovieSearch object.
     */
    @GET("/")
    suspend fun getMoviesListByType(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String = "batman",
        @Query("type") type: String,
    ): Response<MovieSearch>


    /**
     * Retrieves a list of movies released in a specific year and of a specific type.
     *
     * @param apiKey the API key for accessing the OMDB API.
     * @param title the title of the movies to search for (default value is "batman").
     * @param year the year of release of the movies to search for.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a Response containing the search results as a MovieSearch object.
     */
    @GET("/")
    suspend fun getMoviesListByYearAndType(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String = "batman",
        @Query("y") year: String,
        @Query("type") type: String,
    ): Response<MovieSearch>


    /**
     * Companion object containing constants used in the ApiService interface.
     */
    companion object {
        const val API_URL = "https://www.omdbapi.com/"
        const val API_KEY = "3e974fca"
    }
}