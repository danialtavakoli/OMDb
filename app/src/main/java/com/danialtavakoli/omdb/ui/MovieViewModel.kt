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
 * MovieViewModel is a ViewModel class responsible for managing movie-related data.
 * It interacts with the repository to fetch movies list and movie details.
 *
 * @param repository The repository responsible for fetching movie data.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _moviesList = MutableStateFlow<List<Movie>>(listOf())
    val moviesList: StateFlow<List<Movie>> = _moviesList

    private val _movieDetails = MutableStateFlow(provideMockMovieDetails())
    val movieDetails: StateFlow<MovieDetails> = _movieDetails


    /**
     * Fetches a list of movies based on the specified criteria.
     *
     * @param isInternetConnected Boolean value indicating whether internet connectivity is available.
     * @param title The title of the movie to search for.
     * @param year The year of the movie to search for (optional).
     * @param type The type of the movie to search for (optional).
     */
    suspend fun fetchMovies(
        isInternetConnected: Boolean, title: String, year: String? = null, type: String? = null
    ) {
        viewModelScope.launch {
            val moviesList = repository.getMoviesList(
                isInternetConnected = isInternetConnected, title = title, year = year, type = type
            )
            _moviesList.value = moviesList
        }
    }


    /**
     * Fetches details of a specific movie by its IMDb ID.
     *
     * @param isInternetConnected Boolean value indicating whether internet connectivity is available.
     * @param imdbID The IMDb ID of the movie to fetch details for.
     */
    suspend fun fetchMovieDetails(isInternetConnected: Boolean, imdbID: String) {
        viewModelScope.launch {
            val movieDetails = repository.getMovieDetails(
                isInternetConnected = isInternetConnected, imdbId = imdbID
            )
            _movieDetails.value = movieDetails
        }
    }
}