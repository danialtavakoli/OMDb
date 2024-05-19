package com.danialtavakoli.omdb.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%'")
    suspend fun getMoviesList(title: String): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movieDetailsTable WHERE imdbID = :imdbID")
    suspend fun getMovieDetails(imdbID: String): MovieDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)

    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%' AND  year = :year")
    suspend fun getMoviesListByYear(title: String,year:String): List<Movie>?

    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%' AND  type = :type")
    suspend fun getMoviesListByType(title: String,type:String): List<Movie>?

}