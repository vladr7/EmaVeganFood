package com.example.emaveganfood.data.repositories.foodrepository

import android.net.Uri
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.ui.models.FoodImage
import com.example.emaveganfood.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FoodRepository: IFoodRepository {

    companion object {
        const val FIRESTORE_FOODS_COLLECTION = "ALLFOODS"
        const val STORAGE_FOODS = "ALLFOODS"
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val foodCollection = FirebaseFirestore.getInstance()
        .collection(FIRESTORE_FOODS_COLLECTION)

    val storage = FirebaseStorage.getInstance()
    val gsReference = storage.getReferenceFromUrl("gs://emaveganapp.appspot.com/$STORAGE_FOODS/")

    override fun addFood(food: Food) = flow<State<Food>> {
        emit(State.loading())

        foodCollection.document(food.id).set(food)

        emit(State.success(food))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun addFoodImageToStorage(food:Food, fileUri: Uri) = flow<State<StorageReference>> {
        emit(State.loading())

        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("$STORAGE_FOODS/${food.id}$extension")
        refStorage.putFile(fileUri).await()

        emit(State.success(refStorage))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun getAllFoods() = flow<List<Food>>{
        val snapshot = foodCollection.get().await()
        val foods = snapshot.toObjects(Food::class.java)

        emit(foods)
    }.catch {
        emit(listOf())
    }.flowOn(Dispatchers.IO)


    override fun getAllFoodImages() = flow<List<FoodImage>> {
        val mutableListOfFoodImages = mutableListOf<FoodImage>()
        val snapshot = gsReference.listAll().await()
        snapshot.items.forEach { storageReference ->
            mutableListOfFoodImages.add(
                FoodImage(
                    id = storageReference.name.removeSuffix(".jpg"),
                    imageRef = storageReference.downloadUrl.await().toString()
                )
            )
        }

        emit(mutableListOfFoodImages.toList())
    }.catch {
        emit(listOf())
    }.flowOn(Dispatchers.IO)

}