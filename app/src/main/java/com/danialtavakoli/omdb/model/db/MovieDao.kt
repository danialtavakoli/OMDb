/**
 * MovieDao is a Data Access Object (DAO) interface for accessing movie-related data in the Room database.
 */

package com.danialtavakoli.omdb.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails

@Dao
interface MovieDao {

    /**
     * Retrieves a list of movies from the database based on a partial title match.
     *
     * @param title the partial title of the movies to search for.
     * @return a list of movies matching the search criteria, or null if no matches are found.
     */
    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%'")
    suspend fun getMoviesList(title: String): List<Movie>?


    /**
     * Inserts a list of movies into the database.
     *
     * @param movies the list of movies to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)



    /**
     * Retrieves detailed information about a movie from the database.
     *
     * @param imdbID the IMDb ID of the movie to retrieve details for.
     * @return detailed information about the movie, or null if not found.
     */
    @Query("SELECT * FROM movieDetailsTable WHERE imdbID = :imdbID")
    suspend fun getMovieDetails(imdbID: String): MovieDetails?

    /**
     * Inserts detailed information about a movie into the database.
     *
     * @param movieDetails the detailed information about the movie to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)

    /**
     * Retrieves a list of movies from the database based on a partial title match and a specific year.
     *
     * @param title the partial title of the movies to search for.
     * @param year the year of release of the movies to search for.
     * @return a list of movies matching the search criteria, or null if no matches are found.
     */
    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%' AND  year = :year")
    suspend fun getMoviesListByYear(title: String, year: String): List<Movie>?


    /**
     * Retrieves a list of movies from the database based on a partial title match and a specific type.
     *
     * @param title the partial title of the movies to search for.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria, or null if no matches are found.
     */
    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%' AND  type = :type")
    suspend fun getMoviesListByType(title: String, type: String): List<Movie>?

    /**
     * Retrieves a list of movies from the database based on a partial title match, a specific year, and a specific type.
     *
     * @param title the partial title of the movies to search for.
     * @param year the year of release of the movies to search for.
     * @param type the type of the movies to search for (e.g., "movie", "series").
     * @return a list of movies matching the search criteria, or null if no matches are found.
     */
    @Query("SELECT * FROM movieTable WHERE title LIKE '%' || :title || '%' AND  year = :year AND type = :type")
    suspend fun getMoviesListByYearAndType(title: String, year: String, type: String): List<Movie>?

}