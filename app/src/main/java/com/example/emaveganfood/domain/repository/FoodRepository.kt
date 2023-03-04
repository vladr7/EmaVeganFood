package com.example.emaveganfood.domain.repository

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    val foods: Flow<List<Food>>

    suspend fun refreshFoods()

    suspend fun addFood(food: Food): State<Food>

    suspend fun addFoodImageToStorage(food: Food, fileUri: Uri): State<Food>
}