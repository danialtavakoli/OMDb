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
    @Binds
    fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}