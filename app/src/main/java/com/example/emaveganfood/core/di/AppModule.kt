package com.example.emaveganfood.core.di

import android.content.Context
import com.example.emaveganfood.data.repository.FoodRepositoryImpl
import com.example.emaveganfood.data.repository.NetworkConnectionManagerImpl
import com.example.emaveganfood.data.repository.UserRepositoryImpl
import com.example.emaveganfood.domain.repository.IFoodRepository
import com.example.emaveganfood.domain.repository.IUserRepository
import com.example.emaveganfood.domain.repository.INetworkConnectionManager
import com.example.emaveganfood.domain.usecases.foods.*
import com.example.emaveganfood.domain.usecases.navigation.GetStartDestinationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
    fun provideFoodRepository(): IFoodRepository = FoodRepositoryImpl()

    @Provides
    fun provideUserRepository(): IUserRepository = UserRepositoryImpl()

    @Provides
    fun provideNetworkConnectionManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
        ): INetworkConnectionManager =
        NetworkConnectionManagerImpl(
            context,
            coroutineScope
        )

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
    ) = AddFoodToDatabaseUseCase(
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

    @Provides
    fun provideAddFoodToFirebaseCombinedUseCase(
        addFoodToDatabaseUseCase: AddFoodToDatabaseUseCase,
        addFoodImageToStorageUseCase: AddFoodImageToStorageUseCase
    ) = AddFoodCombinedUseCase(
        addFoodToDatabaseUseCase,
        addFoodImageToStorageUseCase
    )
}