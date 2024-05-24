package com.danialtavakoli.omdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails
import com.danialtavakoli.omdb.model.data.Rating
import com.danialtavakoli.omdb.model.db.MovieDao
import com.danialtavakoli.omdb.model.db.MovieDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * MovieDaoTest is an instrumentation test class to test the functionality of MovieDao.
 * It tests the insertion and retrieval of movies and movie details from the database.
 *
 * @see [testing documentation](http://d.android.com/tools/testing) Official documentation for Android testing.
 */
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao


    /**
     * Creates an in-memory MovieDatabase instance before each test.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java).build()
        movieDao = db.movieDao()
    }


    /**
     * Closes the MovieDatabase instance after each test.
     */
    @After
    fun closeDb() {
        db.close()
    }


    /**
     * Test method to verify the insertion and retrieval of movies from the database.
     */
    @Test
    fun testInsertAndGetMovies() = runBlocking {
        val movie =
            Movie(imdbID = "tt1375666", title = "Inception", year = "2010", poster = "", type = "")
        movieDao.insertMovies(listOf(movie))

        val movies = movieDao.getMoviesList("Inception")
        assertNotNull(movies)
        assertEquals(1, movies?.size)
        assertEquals("Inception", movies?.get(0)?.title)
    }


    /**
     * Test method to verify the insertion and retrieval of movie details from the database.
     */
    @Test
    fun testInsertAndGetMovieDetails() = runBlocking {
        val movieDetails = MovieDetails(
            title = "Inception",
            year = "2010",
            rated = "PG-13",
            released = "16 Jul 2010",
            runtime = "148 min",
            genre = "Action, Adventure, Sci-Fi",
            director = "Christopher Nolan",
            writer = "Christopher Nolan",
            actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
            plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
            language = "English, Japanese, French",
            country = "USA, UK",
            awards = "Won 4 Oscars. Another 152 wins & 204 nominations.",
            poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0OF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg",
            ratings = listOf(
                Rating(source = "Internet Movie Database", value = "8.8/10"),
                Rating(source = "Rotten Tomatoes", value = "87%"),
                Rating(source = "Metacritic", value = "74/100")
            ),
            metaScore = "74",
            imdbRating = "8.8",
            imdbVotes = "2,000,000",
            imdbID = "tt1375666",
            type = "movie",
            dvd = "N/A",
            boxOffice = "N/A",
            production = "Syncopy, Warner Bros.",
            website = "N/A",
            response = "True"
        )

        movieDao.insertMovieDetails(movieDetails)

        val retrievedDetails = movieDao.getMovieDetails("tt1375666")
        assertNotNull(retrievedDetails)
        assertEquals("Inception", retrievedDetails?.title)
    }
}