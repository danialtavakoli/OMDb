package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.provideMockMovieDetails
import com.danialtavakoli.omdb.model.db.MovieDao
import com.danialtavakoli.omdb.model.net.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of the [MovieRepository] interface.
 *
 * This class provides methods to fetch movie data from a local database or a remote API,
 * depending on the availability of an internet connection.
 *
 * @property apiService The API service used to fetch data from the OMDB API.
 * @property movieDao The DAO used to interact with the local movie database.
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
) : MovieRepository {

    /**
     * Retrieves a list of movies that match the specified title, year, and type.
     *
     * If the device is connected to the internet and no local data is found, it fetches data from the OMDB API.
     * The fetched data is then saved to the local database.
     *
     * @param isInternetConnected A boolean indicating if the device is connected to the internet.
     * @param title The title of the movies to search for. This is a required parameter.
     * @param year The year of the movies to search for. This is an optional parameter.
     * @param type The type of the movies to search for. This is an optional parameter.
     * @return A list of movies that match the search criteria.
     * @throws Exception If the remote API request fails.
     */
    override suspend fun getMoviesList(
        isInternetConnected: Boolean, title: String, year: String?, type: String?
    ): List<Movie> {
        val localMovies = movieDao.getMoviesList(title = title, year = year, type = type)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies = apiService.getMoviesList(title = title, year = year, type = type)
            if (remoteMovies.isSuccessful) {
                val movies = remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }


    /**
     * Retrieves the details of a movie by its IMDb ID.
     *
     * If the device is connected to the internet and no local data is found, it fetches data from the OMDB API.
     * The fetched data is then saved to the local database.
     *
     * @param isInternetConnected A boolean indicating if the device is connected to the internet.
     * @param imdbId The IMDb ID of the movie to retrieve details for. This is a required parameter.
     * @return The details of the movie with the specified IMDb ID.
     * @throws Exception If the remote API request fails or the movie details are not found.
     */
    override suspend fun getMovieDetails(
        isInternetConnected: Boolean, imdbId: String
    ): MovieDetails {
        val localMovieDetails = movieDao.getMovieDetails(imdbID = imdbId)
        return if (localMovieDetails == null && isInternetConnected) {
            val response = apiService.getMovieDetails(imdbID = imdbId)
            if (response.isSuccessful) {
                val remoteMovieDetails =
                    response.body() ?: throw Exception("Movie details not found")
                movieDao.insertMovieDetails(remoteMovieDetails)
                remoteMovieDetails
            } else throw Exception(response.errorBody()?.string() ?: "Unknown error occurred")
        } else localMovieDetails ?: provideMockMovieDetails()
    }
}