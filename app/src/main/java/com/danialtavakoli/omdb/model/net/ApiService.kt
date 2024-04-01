package com.danialtavakoli.omdb.model.net

import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.MovieSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun getMoviesList(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("s") title: String,
    ): Response<MovieSearch>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("i") imdbID: String,
    ): Response<MovieDetails>

    companion object {
        const val API_URL = "https://www.omdbapi.com/"
        const val API_KEY = "3e974fca"
    }
}