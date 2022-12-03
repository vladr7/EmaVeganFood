package com.example.emaveganfood.di

import com.example.emaveganfood.data.repositories.foodrepository.FoodRepository
import com.example.emaveganfood.data.repositories.foodrepository.IFoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideFoodRepository(): IFoodRepository = FoodRepository()
}