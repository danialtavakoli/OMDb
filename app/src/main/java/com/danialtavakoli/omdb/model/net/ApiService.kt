package com.danialtavakoli.omdb.model.net

import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.MovieSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * ApiService is an interface that defines methods for interacting with the OMDB API.
 *
 * It uses Retrofit annotations to define the API endpoints and parameters.
 */
interface ApiService {

    /**
     * Retrieves a list of movies that match the specified title, year, and type from the OMDB API.
     *
     * @param apiKey The API key for accessing the OMDB API. Default value is [API_KEY].
     * @param title The title of the movies to search for. This is a required parameter.
     * @param year The year of the movies to search for. This is an optional parameter.
     * @param type The type of the movies to search for. This is an optional parameter.
     * @return A [Response] containing a [MovieSearch] object with the search results.
     */
    @GET("/")
    suspend fun getMoviesList(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String,
        @Query("y") year: String? = null,
        @Query("type") type: String? = null
    ): Response<MovieSearch>


    /**
     * Retrieves the details of a movie by its IMDb ID from the OMDB API.
     *
     * @param apiKey The API key for accessing the OMDB API. Default value is [API_KEY].
     * @param imdbID The IMDb ID of the movie to retrieve details for. This is a required parameter.
     * @return A [Response] containing a [MovieDetails] object with the movie details.
     */
    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("i") imdbID: String,
    ): Response<MovieDetails>

    companion object {
        /**
         * The base URL for the OMDB API.
         */
        const val API_URL = "https://www.omdbapi.com/"

        /**
         * The API key for accessing the OMDB API.
         */
        const val API_KEY = "3e974fca"
    }
}