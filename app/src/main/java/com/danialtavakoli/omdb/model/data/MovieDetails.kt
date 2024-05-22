/**
 * MovieDetails is a data class that represents detailed information about a movie entity in the application.
 * It is annotated as a Room entity with the table name "movieDetailsTable" and uses "imdbID" as the primary key.
 */

package com.danialtavakoli.omdb.model.data

import androidx.room.Entity
import androidx.room.vo.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieDetailsTable", primaryKeys = ["imdbID"])
data class MovieDetails(
    /**
     * The title of the movie.
     */
    @SerializedName("Title") val title: String,

    /**
     * The year the movie was released.
     */
    @SerializedName("Year") val year: String,

    /**
     * The rating of the movie.
     */
    @SerializedName("Rated") val rated: String,

    /**
     * The release date of the movie.
     */
    @SerializedName("Released") val released: String,

    /**
     * The runtime of the movie.
     */
    @SerializedName("Runtime") val runtime: String,

    /**
     * The genre of the movie.
     */
    @SerializedName("Genre") val genre: String,

    /**
     * The director of the movie.
     */
    @SerializedName("Director") val director: String,

    /**
     * The writer of the movie.
     */
    @SerializedName("Writer") val writer: String,

    /**
     * The main actors in the movie.
     */
    @SerializedName("Actors") val actors: String,

    /**
     * The plot summary of the movie.
     */
    @SerializedName("Plot") val plot: String,

    /**
     * The language of the movie.
     */
    @SerializedName("Language") val language: String,

    /**
     * The country where the movie was produced.
     */
    @SerializedName("Country") val country: String,

    /**
     * The awards won by the movie.
     */
    @SerializedName("Awards") val awards: String,

    /**
     * The URL of the movie's poster image.
     */
    @SerializedName("Poster") val poster: String,

    /**
     * The list of ratings for the movie.
     */
    @SerializedName("Ratings") val ratings: List<Rating>,

    /**
     * The Metascore rating of the movie.
     */
    @SerializedName("Metascore") val metaScore: String,

    /**
     * The IMDb rating of the movie.
     */
    @SerializedName("imdbRating") val imdbRating: String,

    /**
     * The number of IMDb votes for the movie.
     */
    @SerializedName("imdbVotes") val imdbVotes: String,

    /**
     * The IMDb ID of the movie, used as the primary key in the database.
     */
    @SerializedName("imdbID") val imdbID: String,

    /**
     * The type of the movie (e.g., movie, series).
     */
    @SerializedName("Type") val type: String,

    /**
     * The DVD release date of the movie.
     */
    @SerializedName("DVD") val dvd: String?,

    /**
     * The box office earnings of the movie.
     */
    @SerializedName("BoxOffice") val boxOffice: String?,

    /**
     * The production company of the movie.
     */
    @SerializedName("Production") val production: String?,

    /**
     * The official website of the movie.
     */
    @SerializedName("Website") val website: String?,

    /**
     * The response status.
     */
    @SerializedName("Response") val response: String
)


/**
 * Provides a mock instance of MovieDetails for testing or placeholder purposes.
 *
 * @return a MovieDetails instance with empty or default values.
 */
fun provideMockMovieDetails(): MovieDetails {
    return MovieDetails(
        title = "",
        year = "",
        rated = "",
        released = "",
        runtime = "",
        genre = "",
        director = "",
        writer = "",
        actors = "",
        plot = "",
        language = "",
        country = "",
        awards = "",
        poster = "",
        ratings = listOf(Rating(source = "", value = "")),
        metaScore = "",
        imdbRating = "",
        imdbVotes = "",
        imdbID = "",
        type = "",
        dvd = "",
        boxOffice = "",
        production = "",
        website = "",
        response = ""
    )
}