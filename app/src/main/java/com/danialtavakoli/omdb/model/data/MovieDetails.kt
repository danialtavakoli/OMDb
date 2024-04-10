package com.danialtavakoli.omdb.model.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieDetailsTable", primaryKeys = ["imdbID"])
data class MovieDetails(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Ratings") val ratings: List<Rating>,
    @SerializedName("Metascore") val metaScore: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbVotes") val imdbVotes: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("DVD") val dvd: String?,
    @SerializedName("BoxOffice") val boxOffice: String?,
    @SerializedName("Production") val production: String?,
    @SerializedName("Website") val website: String?,
    @SerializedName("Response") val response: String
)

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