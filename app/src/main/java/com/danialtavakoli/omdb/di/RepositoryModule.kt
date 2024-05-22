/**
 * RepositoryModules is a Dagger module that provides repository dependencies for ViewModel components.
 * It binds the implementation of the MovieRepository interface to its concrete implementation MovieRepositoryImpl.
 */

package com.danialtavakoli.omdb.di

import com.danialtavakoli.omdb.model.repository.MovieRepository
import com.danialtavakoli.omdb.model.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModules {

    /**
     * Binds the MovieRepositoryImpl implementation to the MovieRepository interface.
     *
     * @param repository the MovieRepositoryImpl instance.
     * @return the bound MovieRepository instance.
     */
    @Binds
    fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}