package com.example.emaveganfood.core.di

import com.example.emaveganfood.data.repository.FoodRepositoryImpl
import com.example.emaveganfood.data.repository.UserRepositoryImpl
import com.example.emaveganfood.domain.repository.IFoodRepository
import com.example.emaveganfood.domain.repository.IUserRepository
import com.example.emaveganfood.domain.usecases.foods.*
import com.example.emaveganfood.domain.usecases.navigation.GetStartDestinationUseCase
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
    fun provideUserRepository(): IUserRepository = UserRepositoryImpl()

    @Provides
    fun provideGetStartDestinationUseCase(
        userRepository: IUserRepository
    ) = GetStartDestinationUseCase(
        userRepository
    )

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

    @Provides
    fun provideCheckFieldsAreFilledUseCase() = CheckFieldsAreFilledUseCase()

    @Provides
    fun provideAddFoodUseCase(
        foodsRepository: IFoodRepository,
        checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase
    ) = AddFoodUseCase(
        foodsRepository,
        checkFieldsAreFilledUseCase
    )

    @Provides
    fun provideAddFoodImageToStorageUseCase(
        foodsRepository: IFoodRepository,
        checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase
    ) = AddFoodImageToStorageUseCase(
        foodsRepository,
        checkFieldsAreFilledUseCase
    )
}