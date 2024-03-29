package com.danialtavakoli.omdb.model.data

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<Movie>,
    @SerializedName("totalResults")
    val totalResults: String
)