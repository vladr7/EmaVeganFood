package com.example.emaveganfood.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddFoodViewModel: ViewModel() {

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

}