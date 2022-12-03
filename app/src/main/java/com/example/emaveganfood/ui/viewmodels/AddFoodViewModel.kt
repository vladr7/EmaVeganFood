package com.example.emaveganfood.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.emaveganfood.data.repositories.foodrepository.FoodRepository
import com.example.emaveganfood.data.repositories.foodrepository.IFoodRepository
import com.example.emaveganfood.ui.models.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodRepository: IFoodRepository
): ViewModel() {

    var foodTitle by mutableStateOf("")
        private set

    var foodDescription by mutableStateOf("")
        private set

    fun updateFoodTitle(title: String) {
        foodTitle = title
    }

    fun updateFoodDescription(description: String) {
        foodDescription = description
    }

    fun addFood(food: Food)  = foodRepository.addFood(food)

}