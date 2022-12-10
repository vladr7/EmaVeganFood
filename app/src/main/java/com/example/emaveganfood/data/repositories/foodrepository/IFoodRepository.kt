package com.example.emaveganfood.data.repositories.foodrepository

import android.net.Uri
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.ui.models.FoodImage
import com.example.emaveganfood.utils.State
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IFoodRepository {

    fun addFood(food: Food): Flow<State<Food>>

    fun addFoodImageToStorage(food:Food, fileUri: Uri): Flow<State<StorageReference>>

    fun getAllFoods(): Flow<List<Food>>

    fun getAllFoodImages(): Flow<List<FoodImage>>
}