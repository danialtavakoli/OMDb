package com.danialtavakoli.omdb.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails


/**
 * Data Access Object (DAO) for the Movie entity.
 *
 * This interface provides methods for interacting with the Movie data in the database.
 * It uses the Room persistence library annotations to map method calls to SQL queries.
 */
@Dao
interface MovieDao {


    /**
     * Retrieves a list of movies that match the specified title, year, and type.
     *
     * @param title The title of the movie to search for. This is a required parameter.
     * @param year The year of the movie to search for. This is an optional parameter.
     * @param type The type of the movie to search for. This is an optional parameter.
     * @return A list of movies that match the search criteria, or null if no matches are found.
     */
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


    /**
     * Inserts a list of movies into the database.
     *
     * If a movie already exists in the database with the same primary key, it will be replaced.
     *
     * @param movies The list of movies to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)


    /**
     * Retrieves the details of a movie by its IMDb ID.
     *
     * @param imdbID The IMDb ID of the movie to retrieve details for.
     * @return The details of the movie with the specified IMDb ID, or null if no such movie exists.
     */
    @Query("SELECT * FROM movieDetailsTable WHERE imdbID = :imdbID")
    suspend fun getMovieDetails(imdbID: String): MovieDetails?


    /**
     * Inserts the details of a movie into the database.
     *
     * If the movie details already exist in the database with the same primary key, they will be replaced.
     *
     * @param movieDetails The details of the movie to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)
}