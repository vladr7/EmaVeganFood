package com.example.emaveganfood.domain.repository

import android.net.Uri
import com.example.emaveganfood.presentation.models.Food
import com.example.emaveganfood.presentation.models.FoodImage
import com.example.emaveganfood.core.utils.State
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface IFoodRepository {

    fun addFood(food: Food): Flow<State<Food>>

    fun addFoodImageToStorage(food: Food, fileUri: Uri): Flow<State<StorageReference>>

    fun getAllFoods(): Flow<State<List<Food>>>

    fun getAllFoodImages(): Flow<List<FoodImage>>
}