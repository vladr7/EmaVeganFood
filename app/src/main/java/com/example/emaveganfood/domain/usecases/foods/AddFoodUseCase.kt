package com.example.emaveganfood.domain.usecases.foods

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFoodUseCase @Inject constructor(
    private val checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase,
    private val foodRepository: FoodRepository
) {

    suspend fun execute(food: Food, imageUri: Uri?): State<Food> {
        if(!checkFieldsAreFilledUseCase(food.title, food.description)) {
            return State.failed("Te rog adauga titlu si o scurta descriere")
        }

        if(imageUri == null) {
            return State.failed("Te rog adauga o poza cu reteta")
        }

        foodRepository.addFood(food)
        return foodRepository.addFoodImageToStorage(food, imageUri)
    }
}
