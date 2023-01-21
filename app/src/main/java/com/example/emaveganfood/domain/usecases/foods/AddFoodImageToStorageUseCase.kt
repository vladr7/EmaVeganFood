package com.example.emaveganfood.domain.usecases.foods

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.domain.repository.FoodRepository
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddFoodImageToStorageUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    private val checkFieldsAreFilledUseCase: CheckFieldsAreFilledUseCase
    ) {

    operator fun invoke(food: Food, fileUri: Uri?): Flow<State<Food>> {
        if(!checkFieldsAreFilledUseCase(food.title, food.description)) {
            return flowOf(State.failed("Te rog adauga titlu si o scurta descriere"))
        }

        if(fileUri == null) {
            return flowOf(State.failed("Te rog adauga o poza cu reteta"))
        }

        return foodRepository.addFoodImageToStorage(food, fileUri)
    }
}