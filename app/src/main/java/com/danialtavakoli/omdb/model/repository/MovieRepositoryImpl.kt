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
    override suspend fun getMoviesList(title: String, isInternetConnected: Boolean): List<Movie> {
        val localMovies = movieDao.getMoviesList(title = title)
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            //get from net
            val remoteMovies = apiService.getMoviesList(title = title)
            if (remoteMovies.isSuccessful) {
                val movies =
                    remoteMovies.body()?.search ?: listOf()
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
            //get from local
        } else localMovies
    }

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
}