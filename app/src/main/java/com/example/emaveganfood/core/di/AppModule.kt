package com.example.emaveganfood.core.di

import android.content.Context
import com.example.emaveganfood.data.repository.DefaultFoodRepository
import com.example.emaveganfood.data.repository.DefaultNetworkConnectionManager
import com.example.emaveganfood.data.repository.DefaultUserRepository
import com.example.emaveganfood.domain.repository.FoodRepository
import com.example.emaveganfood.domain.repository.UserRepository
import com.example.emaveganfood.domain.repository.NetworkConnectionManager
import com.example.emaveganfood.domain.usecases.foods.*
import com.example.emaveganfood.domain.usecases.navigation.GetStartDestinationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Provides
    fun provideFoodRepository(): FoodRepository = DefaultFoodRepository()

    @Provides
    fun provideUserRepository(): UserRepository = DefaultUserRepository()

    @Provides
    fun provideNetworkConnectionManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
        ): NetworkConnectionManager =
        DefaultNetworkConnectionManager(
            context,
            coroutineScope
        )

    @Provides
    fun provideGetStartDestinationUseCase(
        userRepository: UserRepository
    ) = GetStartDestinationUseCase(
        userRepository
    )

    @Provides
    fun provideGetAllFoodsUseCase(
        foodsRepository: FoodRepository
    ) = GetAllFoodsUseCase(
        foodsRepository
    )

    @Provides
    fun provideGetAllFoodImagesUseCase(
        foodsRepository: FoodRepository
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
        foodsRepository: FoodRepository,
        checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase
    ) = AddFoodToDatabaseUseCase(
        foodsRepository,
        checkFieldsAreFilledUseCase
    )

    @Provides
    fun provideAddFoodImageToStorageUseCase(
        foodsRepository: FoodRepository,
        checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase
    ) = AddFoodImageToStorageUseCase(
        foodsRepository,
        checkFieldsAreFilledUseCase
    )

    @Provides
    fun provideAddFoodToFirebaseCombinedUseCase(
        addFoodToDatabaseUseCase: AddFoodToDatabaseUseCase,
        addFoodImageToStorageUseCase: AddFoodImageToStorageUseCase
    ) = AddFoodCombinedUseCase(
        addFoodToDatabaseUseCase,
        addFoodImageToStorageUseCase
    )

    @Provides
    fun provideGenerateFoodUseCase(

    ) = GenerateFoodUseCase(

    )
}