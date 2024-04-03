package com.danialtavakoli.omdb.model.repository

import com.danialtavakoli.omdb.model.data.Movie
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
        val localMovies = movieDao.getMoviesList()
        return if (localMovies?.isEmpty()!! && isInternetConnected) {
            val remoteMovies = apiService.getMoviesList(title = title)
            if (remoteMovies.isSuccessful) {
                val movies =
                    remoteMovies.body()?.search ?: throw Exception("Movie details not found")
                movieDao.insertMovies(movies)
                movies
            } else throw Exception(remoteMovies.errorBody()?.string() ?: "Unknown error occurred")
        } else localMovies
    }
}