/**
 * MovieRepositoryImpl is an implementation of the MovieRepository interface.
 * It provides methods for retrieving movie data from local databases and remote APIs.
 *
 * @property apiService an instance of ApiService used to make network requests.
 * @property movieDao an instance of MovieDao used to perform database operations.
 */

package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.provideMockMovieDetails
import com.danialtavakoli.omdb.model.db.MovieDao
import com.danialtavakoli.omdb.model.net.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
) : MovieRepository {

    /**
     * Retrieves a list of movies based on a search query.
     *
     * @param title the title of the movies to search for.
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @return a list of movies matching the search criteria.
     */
    override suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie> {
        val localMovies = movieDao.getMoviesList(title = title)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies = apiService.getMoviesList(title = title)
            if (remoteMovies.isSuccessful) {
                val movies = remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }


    /**
     * Retrieves detailed information about a movie based on its IMDb ID.
     *
     * @param imdbId the IMDb ID of the movie to retrieve details for.
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @return detailed information about the movie.
     */
    override suspend fun getMovieDetails(
        imdbId: String,
        isInternetConnected: Boolean,
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
        } else if (!isInternetConnected && localMovieDetails == null) provideMockMovieDetails()
        else localMovieDetails ?: throw Exception("Local movie details not found")
    }


    /**
     * Retrieves a list of movies released in a specific year.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param year the year of release of the movies to search for.
     * @return a list of movies matching the search criteria.
     */
    override suspend fun getMoviesListByYear(
        title: String,
        isInternetConnected: Boolean,
        year: String
    ): List<Movie> {
        val localMovies = movieDao.getMoviesListByYear(title = title, year = year)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies = apiService.getMoviesListByYear(title = title, year = year)
            if (remoteMovies.isSuccessful) {
                val movies = remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }


    /**
     * Retrieves a list of movies of a specific type.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria.
     */
    override suspend fun getMoviesListByType(
        title: String,
        isInternetConnected: Boolean,
        type: String
    ): List<Movie> {
        val localMovies = movieDao.getMoviesListByType(title = title, type = type)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies = apiService.getMoviesListByType(title = title, type = type)
            if (remoteMovies.isSuccessful) {
                val movies = remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }


    /**
     * Retrieves a list of movies released in a specific year and of a specific type.
     *
     * @param title the title of the movies to search for (default value is "batman").
     * @param isInternetConnected a flag indicating whether the device is connected to the internet.
     * @param year the year of release of the movies to search for.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria.
     */
    override suspend fun getMoviesListByYearAndType(
        title: String,
        isInternetConnected: Boolean,
        year: String,
        type: String
    ): List<Movie> {
        val localMovies =
            movieDao.getMoviesListByYearAndType(title = title, year = year, type = type)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies =
                apiService.getMoviesListByYearAndType(title = title, year = year, type = type)
            if (remoteMovies.isSuccessful) {
                val movies = remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }
}