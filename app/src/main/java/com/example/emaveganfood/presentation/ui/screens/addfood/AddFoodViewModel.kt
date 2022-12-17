package com.example.emaveganfood.presentation.ui.screens.addfood

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.emaveganfood.domain.repository.IFoodRepository
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.core.utils.State
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
            return flowOf(State.failed("Te rog adauga titlu si o scurta descriere"))
        }

        if(fileUri == null) {
            return flowOf(State.failed("Te rog adauga o poza cu reteta"))
        }

        return foodRepository.addFood(food)
    }

    fun addFoodImageToStorage(food: Food = foodItem, fileUri: Uri?): Flow<State<StorageReference>> {
        if(!checkFieldsAreFilled()) {
            return flowOf(State.failed("Te rog adauga titlu si o scurta descriere"))
        }

        if(fileUri == null) {
            return flowOf(State.failed("Te rog adauga o poza cu reteta"))
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