/**
 * MovieSearch is a data class that represents the response from a movie search query.
 * It contains the search results, the total number of results, and a response status.
 */

package com.danialtavakoli.omdb.model.data

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    /**
     * The response status of the search query.
     */
    @SerializedName("Response")
    val response: String,

    /**
     * The list of movies returned by the search query.
     */
    @SerializedName("Search")
    val search: List<Movie>,

    /**
     * The total number of search results.
     */
    @SerializedName("totalResults")
    val totalResults: String
)