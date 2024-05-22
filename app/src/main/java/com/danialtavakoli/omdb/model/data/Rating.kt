/**
 * Rating is a data class that represents a rating for a movie.
 * It includes the source of the rating and the rating value.
 */

package com.danialtavakoli.omdb.model.data

import com.google.gson.annotations.SerializedName

data class Rating(

    /**
     * The source of the rating (e.g., "Internet Movie Database").
     */
    @SerializedName("Source") val source: String,

    /**
     * The value of the rating (e.g., "8.5/10").
     */
    @SerializedName("Value") val value: String
)
