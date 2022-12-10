package com.example.emaveganfood.di

import com.example.emaveganfood.data.repositories.foodrepository.FoodRepository
import com.example.emaveganfood.data.repositories.foodrepository.IFoodRepository
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodImagesUseCase
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsUseCase
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsWithImagesCombinedUseCase
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

    @Provides
    fun provideGetAllFoodsUseCase(
        foodsRepository: IFoodRepository
    ) = GetAllFoodsUseCase(
        foodsRepository
    )

    @Provides
    fun provideGetAllFoodImagesUseCase(
        foodsRepository: IFoodRepository
    ) = GetAllFoodImagesUseCase(
        foodsRepository
    )

    @Provides
    fun provideGetAllFoodsWithImagesCombinedUseCase(
        getAllFoodsUseCase: GetAllFoodsUseCase,
        getAllFoodImagesUseCase: GetAllFoodImagesUseCase
    ) = GetAllFoodsWithImagesCombinedUseCase(
        getAllFoodsUseCase,
        getAllFoodImagesUseCase
    )
}