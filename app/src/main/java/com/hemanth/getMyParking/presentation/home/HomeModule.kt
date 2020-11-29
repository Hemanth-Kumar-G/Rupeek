package com.hemanth.getMyParking.presentation.home

import com.hemanth.getMyParking.data.model.BookResponse
import com.hemanth.getMyParking.presentation.home.adapter.BookAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {

    @ActivityScoped
    @Provides
    fun provideList(): ArrayList<BookResponse.Item> = ArrayList()

    @ActivityScoped
    @Provides
    fun provideBookAdapter(list: ArrayList<BookResponse.Item>) = BookAdapter(list)
}