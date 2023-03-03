package com.example.emaveganfood.data.repository

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.datasource.FoodDataSource
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.data.models.FoodImage
import com.example.emaveganfood.database.FoodDatabase
import com.example.emaveganfood.database.asDatabaseModel
import com.example.emaveganfood.database.asDomainModel
import com.example.emaveganfood.domain.repository.NewFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultNewFoodRepository @Inject constructor(
    private val database: FoodDatabase,
    private val foodDataSource: FoodDataSource
) : NewFoodRepository {

    override val foods: Flow<List<Food>> = database.foodDao.getFoods().map { databaseList ->
        databaseList.asDomainModel()
    }

    override suspend fun refreshFoods() {
        withContext(Dispatchers.IO) {
            foodDataSource.getAllFoods()
                .combine(foodDataSource.getAllFoodImages()) { foods, images ->
                    val mutableListOfFoods = mutableListOf<Food>()
                    foods.forEach { food ->
                        val image = images.find { image ->
                            food.id == image.id
                        }
                        val newFood = Food(
                            id = food.id,
                            title = food.title,
                            description = food.description,
                            imageRef = image?.imageRef ?: ""
                        )
                        if (newFood.imageRef.isNotEmpty()) {
                            mutableListOfFoods.add(newFood)
                        }
                    }
                    if (mutableListOfFoods.isNotEmpty()) {
                        database.foodDao.insertAll(
                            mutableListOfFoods.toList().asDatabaseModel()
                        )
                    }
                }.collect()
        }
    }

    override fun addFood(food: Food): Flow<State<Food>> =
        foodDataSource.addFood(food = food)

    override fun addFoodImageToStorage(food: Food, fileUri: Uri): Flow<State<Food>> =
        foodDataSource.addFoodImageToStorage(food = food, fileUri = fileUri)
}
