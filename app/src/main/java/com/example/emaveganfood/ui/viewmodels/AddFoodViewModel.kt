package com.example.emaveganfood.ui.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.emaveganfood.data.repositories.foodrepository.IFoodRepository
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.utils.State
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodRepository: IFoodRepository
): ViewModel() {

    var foodTitle by mutableStateOf("")
        private set

    var foodDescription by mutableStateOf("")
        private set

    var foodItem by mutableStateOf(Food(id = UUID.randomUUID().toString()))
        private set

    fun updateFoodTitle(title: String) {
        foodTitle = title
        foodItem = foodItem.copy(title = foodTitle)
    }

    fun updateFoodDescription(description: String) {
        foodDescription = description
        foodItem = foodItem.copy(description = foodDescription)
    }

    fun addFood(food: Food = foodItem, fileUri: Uri?): Flow<State<Food>> {
        if(!checkFieldsAreFilled()) {
            return flowOf(State.failed("Please add title and description"))
        }

        if(fileUri == null) {
            return flowOf(State.failed("Please add image"))
        }

        return foodRepository.addFood(food)
    }

    fun addFoodImageToStorage(food: Food = foodItem, fileUri: Uri?): Flow<State<StorageReference>> {
        if(!checkFieldsAreFilled()) {
            return flowOf(State.failed("Please add title and description"))
        }

        if(fileUri == null) {
            return flowOf(State.failed("Please add image"))
        }

        return foodRepository.addFoodImageToStorage(food, fileUri)
    }

    private fun checkFieldsAreFilled(): Boolean {
        if(foodItem.title.isEmpty() || foodDescription.isEmpty()) {
            return false
        }
        return true
    }

}