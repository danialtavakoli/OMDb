
/**
 * Converters is a class that contains methods to convert custom data types to and from
 * JSON strings for storage in a Room database.
 */
package com.danialtavakoli.omdb.model.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    /**
     * Converts a list of Rating objects to a JSON string.
     *
     * @param ratings the list of Rating objects to be converted.
     * @return the JSON string representation of the list of Rating objects.
     */
    @TypeConverter
    fun fromRatingsList(ratings: List<Rating>): String {
        return Gson().toJson(ratings)
    }


    /**
     * Converts a JSON string to a list of Rating objects.
     *
     * @param json the JSON string to be converted.
     * @return the list of Rating objects represented by the JSON string.
     */
    @TypeConverter
    fun toRatingsList(json: String): List<Rating> {
        val type = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(json, type)
    }
}
