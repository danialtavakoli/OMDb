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

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _moviesList = MutableStateFlow<List<Movie>>(listOf())
    val moviesList: StateFlow<List<Movie>> = _moviesList

    private val _movieDetails = MutableStateFlow(provideMockMovieDetails())
    val movieDetails: StateFlow<MovieDetails> = _movieDetails

    suspend fun fetchMovies(title: String, isInternetConnected: Boolean) {
        viewModelScope.launch {
            val moviesList =
                repository.getMoviesList(title = title, isInternetConnected = isInternetConnected)
            _moviesList.value = moviesList
        }
    }

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

    suspend fun fetchMovieDetails(imdbID: String, isInternetConnected: Boolean) {
        viewModelScope.launch {
            val movieDetails = repository.getMovieDetails(
                imdbId = imdbID, isInternetConnected = isInternetConnected,
            )
            _movieDetails.value = movieDetails
        }
    }

}