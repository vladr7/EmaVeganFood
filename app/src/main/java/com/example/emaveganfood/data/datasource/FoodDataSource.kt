package com.example.emaveganfood.data.datasource

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.data.models.FoodImage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface FoodDataSource {

    fun addFood(food: Food): Flow<State<Food>>

    fun addFoodImageToStorage(food: Food, fileUri: Uri): Flow<State<StorageReference>>

    fun getAllFoods(): Flow<State<List<Food>>>

    fun getAllFoodImages(): Flow<List<FoodImage>>
}