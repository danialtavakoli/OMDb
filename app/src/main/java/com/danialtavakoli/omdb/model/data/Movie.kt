/**
 * Movie is a data class that represents a movie entity in the application.
 * It is annotated as a Room entity with the table name "movieTable" and uses "imdbID" as the primary key.
 */
package com.danialtavakoli.omdb.model.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieTable", primaryKeys = ["imdbID"])
data class Movie(
    /**
     * The title of the movie.
     */
    @SerializedName("Title") val title: String,
    /**
     * The year the movie was released.
     */
    @SerializedName("Year") val year: String,
    /**
     * The IMDb ID of the movie, used as the primary key in the database.
     */
    @SerializedName("imdbID") val imdbID: String,
    /**
     * The type of the movie (e.g., movie, series).
     */
    @SerializedName("Type") val type: String,
    /**
     * The URL of the movie's poster image.
     */
    @SerializedName("Poster") val poster: String,
)
