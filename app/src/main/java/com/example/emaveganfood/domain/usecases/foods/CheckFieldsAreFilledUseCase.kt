package com.example.emaveganfood.domain.usecases.foods

class CheckFieldsAreFilledUseCase {

    operator fun invoke(foodTitle: String, foodDescription: String): Boolean {
        if(foodTitle.isEmpty() || foodDescription.isEmpty()) {
            return false
        }
        return true
    }
}