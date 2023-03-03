package com.example.emaveganfood.data.datasource

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.data.models.FoodImage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface FoodDataSource {

    suspend fun addFood(food: Food): State<Food>

    suspend fun addFoodImageToStorage(food: Food, fileUri: Uri): State<Food>

    fun getAllFoods(): Flow<List<Food>>

    fun getAllFoodImages(): Flow<List<FoodImage>>
}