package com.arturomarmolejo.yelpappcompose.di

import com.arturomarmolejo.yelpappcompose.data.repositories.YelpRepository
import com.arturomarmolejo.yelpappcompose.data.repositories.YelpRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesYelpRepositoryImpl(
        yelpRepositoryImpl: YelpRepositoryImpl
    ): YelpRepository
}