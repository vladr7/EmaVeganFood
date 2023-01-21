package com.example.emaveganfood.data.repository

import android.net.Uri
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.data.models.FoodImage
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.datasource.FoodDataSource
import com.example.emaveganfood.domain.repository.FoodRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultFoodRepository @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodRepository {

    override fun addFood(food: Food): Flow<State<Food>> =
        foodDataSource.addFood(food = food)

    /**
     * Example of mapping flow result into another flow without having to collect
     */
    override fun addFoodImageToStorage(food: Food, fileUri: Uri): Flow<State<Food>> =
        foodDataSource.addFoodImageToStorage(food = food, fileUri = fileUri).map { referenceState ->
            when(referenceState){
                is State.Failed -> {
                    State.failed<Food>(message = referenceState.message)
                }
                is State.Loading -> {
                    State.loading<Food>()
                }
                is State.Success -> {
                    State.success(food)
                }
            }
        }

    override fun getAllFoods(): Flow<State<List<Food>>>  =
        foodDataSource.getAllFoods()

    override fun getAllFoodImages(): Flow<List<FoodImage>> =
        foodDataSource.getAllFoodImages()

}