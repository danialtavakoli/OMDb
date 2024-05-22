package com.danialtavakoli.omdb.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

@Dao
interface MovieDao {

    @Query(
        """
        SELECT * FROM movieTable
        WHERE title LIKE '%' || :title || '%'
        AND (:year IS NULL OR year = :year)
        AND (:type IS NULL OR type = :type)
    """
    )
    suspend fun getMoviesList(
        title: String, year: String? = null, type: String? = null
    ): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movieDetailsTable WHERE imdbID = :imdbID")
    suspend fun getMovieDetails(imdbID: String): MovieDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)
}