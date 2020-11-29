package com.hemanth.getMyParking.di

import com.hemanth.getMyParking.data.RepoService
import com.hemanth.getMyParking.data.repository.HomeRepository
import com.hemanth.getMyParking.data.repositoryImpl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun provideHomeRepo(repoService: RepoService): HomeRepository =
        HomeRepositoryImpl(repoService)

}