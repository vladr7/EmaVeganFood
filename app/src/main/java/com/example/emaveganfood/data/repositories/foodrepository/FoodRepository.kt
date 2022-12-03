package com.example.emaveganfood.data.repositories.foodrepository

import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FoodRepository: IFoodRepository {

    companion object {
        const val FIRESTORE_FOODS_COLLECTION = "ALLFOODS"
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val foodCollection = FirebaseFirestore.getInstance()
        .collection(FIRESTORE_FOODS_COLLECTION)

    override fun addFood(food: Food) = flow<State<Food>> {
        emit(State.loading())

        foodCollection.add(food).await()

        emit(State.success(food))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}