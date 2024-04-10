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
    abstract fun movieDao(): MovieDao
}