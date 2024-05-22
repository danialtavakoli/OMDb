/**
 * AppModule is a Dagger module responsible for providing application-level dependencies.
 * It sets up instances of OkHttpClient, Retrofit, ApiService, MovieDatabase, and MovieDao.
 */

package com.danialtavakoli.omdb.di

import android.content.Context
import androidx.room.Room
import com.danialtavakoli.omdb.model.db.MovieDao
import com.danialtavakoli.omdb.model.db.MovieDatabase
import com.danialtavakoli.omdb.model.net.ApiService
import com.danialtavakoli.omdb.model.net.ApiService.Companion.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    /**
     * Provides a singleton instance of OkHttpClient.
     *
     * @return an OkHttpClient with configured timeouts.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit.
     *
     * @param okHttpClient the OkHttpClient to be used by Retrofit.
     * @return a Retrofit instance configured with a base URL and Gson converter.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides a singleton instance of ApiService.
     *
     * @param retrofit the Retrofit instance to create the ApiService.
     * @return an ApiService instance for network operations.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides a singleton instance of MovieDatabase.
     *
     * @param appContext the application context to create the Room database.
     * @return a MovieDatabase instance.
     */
    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movieDatabase"
        ).build()
    }

    /**
     * Provides a singleton instance of MovieDao.
     *
     * @param database the MovieDatabase instance to retrieve the DAO.
     * @return a MovieDao instance for database operations.
     */
    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

}