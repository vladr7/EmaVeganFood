package com.example.emaveganfood.data.repository

import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.datasource.FoodDataSource
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.database.FoodDatabase
import com.example.emaveganfood.database.asDatabaseModel
import com.example.emaveganfood.database.asDomainModel
import com.example.emaveganfood.domain.repository.NewFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultNewFoodRepository @Inject constructor(
    private val database: FoodDatabase,
    private val foodDataSource: FoodDataSource
): NewFoodRepository {

    override val foods: Flow<List<Food>> = database.foodDao.getFoods().map { databaseList ->
        databaseList.asDomainModel()
    }

    override suspend fun refreshFoods() {
        withContext(Dispatchers.IO) {
            foodDataSource.getAllFoods().collectLatest { state ->
                when(state) {
                    is State.Success -> {
                        database.foodDao.insertAll(state.data.asDatabaseModel())
                    }
                    else -> {}
                }
            }
        }
    }
}