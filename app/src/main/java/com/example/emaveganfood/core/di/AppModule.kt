package com.example.emaveganfood.core.di

import com.example.emaveganfood.data.repository.foodrepository.FoodRepositoryImpl
import com.example.emaveganfood.domain.repository.IFoodRepository
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodImagesUseCase
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsUseCase
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsWithImagesCombinedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideFoodRepository(): IFoodRepository = FoodRepositoryImpl()

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