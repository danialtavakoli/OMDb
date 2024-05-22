/**
 * MovieDatabase is an abstract class that serves as the Room database for the application.
 * It is annotated with @Database to specify the entities it contains and the database version.
 * It also specifies the type converters to be used.
 */

package com.danialtavakoli.omdb.model.db

import com.danialtavakoli.omdb.model.data.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.data.MovieDetails


@TypeConverters(Converters::class)
@Database(entities = [Movie::class, MovieDetails::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    /**
     * Provides access to the MovieDao for performing database operations.
     *
     * @return the MovieDao instance.
     */
    abstract fun movieDao(): MovieDao
}