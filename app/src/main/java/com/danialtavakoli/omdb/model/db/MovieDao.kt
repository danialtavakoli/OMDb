package com.danialtavakoli.omdb.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieTable")
    fun getMoviesList(): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movieDetailsTable WHERE imdbID = :imdbID")
    fun getMovieDetails(imdbID: String): MovieDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetails: MovieDetails)

}