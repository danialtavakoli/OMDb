package com.danialtavakoli.omdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.provideMockMovieDetails
import com.danialtavakoli.omdb.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel class responsible for managing movie data and business logic.
 *
 * @property repository The repository for accessing movie-related data.
 * @property _moviesList MutableStateFlow representing the list of movies.
 * @property moviesList Immutable StateFlow representing the list of movies.
 * @property _movieDetails MutableStateFlow representing the details of a movie.
 * @property movieDetails Immutable StateFlow representing the details of a movie.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _moviesList = MutableStateFlow<List<Movie>>(listOf())
    val moviesList: StateFlow<List<Movie>> = _moviesList

    private val _movieDetails = MutableStateFlow(provideMockMovieDetails())
    val movieDetails: StateFlow<MovieDetails> = _movieDetails


    /**
     * Fetches a list of movies based on the specified title.
     *
     * @param title The title used for searching movies.
     * @param isInternetConnected A boolean indicating whether the device is connected to the internet.
     */
    suspend fun fetchMovies(title: String, isInternetConnected: Boolean) {
        viewModelScope.launch {
            val moviesList =
                repository.getMoviesList(title = title, isInternetConnected = isInternetConnected)
            _moviesList.value = moviesList
        }
    }


    /**
     * Fetches a list of movies based on the specified title and year.
     *
     * @param title The title used for searching movies.
     * @param isInternetConnected A boolean indicating whether the device is connected to the internet.
     * @param year The year of release used for filtering movies.
     */
    suspend fun fetchMoviesByYear(title: String, isInternetConnected: Boolean, year: String) {
        viewModelScope.launch {
            val moviesList =
                repository.getMoviesListByYear(
                    title = title,
                    isInternetConnected = isInternetConnected,
                    year = year
                )
            _moviesList.value = moviesList
        }
    }


    /**
     * Fetches a list of movies based on the specified title and type.
     *
     * @param title The title used for searching movies.
     * @param isInternetConnected A boolean indicating whether the device is connected to the internet.
     * @param type The type of movie (e.g., movie, series) used for filtering movies.
     */
    suspend fun fetchMoviesByType(title: String, isInternetConnected: Boolean, type: String) {
        viewModelScope.launch {
            val moviesList =
                repository.getMoviesListByType(
                    title = title,
                    isInternetConnected = isInternetConnected,
                    type = type
                )
            _moviesList.value = moviesList
        }
    }


    /**
     * Fetches a list of movies based on the specified title, year, and type.
     *
     * @param title The title used for searching movies.
     * @param isInternetConnected A boolean indicating whether the device is connected to the internet.
     * @param type The type of movie (e.g., movie, series) used for filtering movies.
     * @param year The year of release used for filtering movies.
     */
    suspend fun fetchMoviesByYearAndType(
        title: String,
        isInternetConnected: Boolean,
        type: String,
        year: String
    ) {
        viewModelScope.launch {
            val moviesList =
                repository.getMoviesListByYearAndType(
                    title = title,
                    isInternetConnected = isInternetConnected,
                    year = year,
                    type = type
                )
            _moviesList.value = moviesList
        }
    }


    /**
     * Fetches details of a movie based on its IMDb ID.
     *
     * @param imdbID The IMDb ID of the movie.
     * @param isInternetConnected A boolean indicating whether the device is connected to the internet.
     */
    suspend fun fetchMovieDetails(imdbID: String, isInternetConnected: Boolean) {
        viewModelScope.launch {
            val movieDetails = repository.getMovieDetails(
                imdbId = imdbID, isInternetConnected = isInternetConnected,
            )
            _movieDetails.value = movieDetails
        }
    }

}