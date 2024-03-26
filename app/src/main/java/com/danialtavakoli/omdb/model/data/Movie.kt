package com.danialtavakoli.omdb.model.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieTable", primaryKeys = ["imdbID"])
data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,
)
