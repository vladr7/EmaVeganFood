package com.example.emaveganfood.core.di

import android.content.Context
import com.example.emaveganfood.data.datasource.FoodDataSource
import com.example.emaveganfood.data.datasource.implementation.DefaultFoodDataSource
import com.example.emaveganfood.data.repository.DefaultNetworkConnectionManager
import com.example.emaveganfood.data.repository.DefaultFoodRepository
import com.example.emaveganfood.data.repository.DefaultUserRepository
import com.example.emaveganfood.data.database.FoodDatabase
import com.example.emaveganfood.data.database.getFoodDatabase
import com.example.emaveganfood.domain.repository.UserRepository
import com.example.emaveganfood.domain.repository.NetworkConnectionManager
import com.example.emaveganfood.domain.repository.FoodRepository
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
    fun provideFoodDataSource(): FoodDataSource = DefaultFoodDataSource()

    @Provides
    fun provideFoodRepository(
        foodDatabase: FoodDatabase,
        foodDataSource: FoodDataSource
    ): FoodRepository = DefaultFoodRepository(
        foodDatabase,
        foodDataSource = foodDataSource
    )

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
    fun provideCheckFieldsAreFilledUseCase() = CheckFieldsAreFilledUseCase()

    @Provides
    fun provideAddFoodToFirebaseCombinedUseCase(
        checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase,
        foodRepository: FoodRepository
    ) = AddFoodUseCase(
        checkFieldsAreFilledUseCase,
        foodRepository
    )

    @Provides
    fun provideGenerateFoodUseCase(
        getAllFoodsUseCase: GetAllFoodsUseCase
    ) = GenerateFoodUseCase(
        getAllFoodsUseCase
    )

    @Singleton
    @Provides
    fun provideFoodDatabase(
        @ApplicationContext context: Context
    ) = getFoodDatabase(context)

}