package com.example.emaveganfood.data.datasource.implementation

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.datasource.FoodDataSource
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.data.models.FoodImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class DefaultFoodDataSource: FoodDataSource {

    companion object {
        const val FIRESTORE_FOODS_COLLECTION = "ALLFOODS"
        const val STORAGE_FOODS = "ALLFOODS"
    }

    private val foodCollection = FirebaseFirestore.getInstance()
        .collection(FIRESTORE_FOODS_COLLECTION)

    private val storage = FirebaseStorage.getInstance()
    private val gsReference = storage.getReferenceFromUrl("gs://emaveganapp.appspot.com/$STORAGE_FOODS/")

    override fun addFood(food: Food) = flow<State<Food>> {
        emit(State.loading())

        foodCollection.document(food.id).set(food)

        emit(State.success(food))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun addFoodImageToStorage(food: Food, fileUri: Uri) = flow<State<StorageReference>> {
        emit(State.loading())

        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("$STORAGE_FOODS/${food.id}$extension")
        refStorage.putFile(fileUri).await()

        emit(State.success(refStorage))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun getAllFoods() = callbackFlow<State<List<Food>>>{
        trySend(State.loading())
        val snapshotListener = foodCollection.addSnapshotListener { snapshot, error ->
            if(snapshot != null) {
                val foods = snapshot.toObjects(Food::class.java)
                trySend(State.success(foods))
            } else {
                trySend(State.failed(error?.message ?: ""))
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        emit(State.failed("failed to get foods"))
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