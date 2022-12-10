package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllFoodsWithImagesCombinedUseCase @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val getAllFoodImagesUseCase: GetAllFoodImagesUseCase
) {

    suspend operator fun invoke() = channelFlow<State<List<Food>>> {
        send(State.loading())

        getAllFoodsUseCase().combine(getAllFoodImagesUseCase()) { foods, images ->
            val mutableListOfFoods = mutableListOf<Food>()
            foods.forEach { food ->
                val image = images.find { image ->
                    food.id == image.id
                }
                val newFood = Food(
                    id = food.id,
                    title = food.title,
                    description = food.description,
                    imageRef = image?.imageRef ?: ""
                )
                if (newFood.imageRef.isNotEmpty()) {
                    mutableListOfFoods.add(newFood)
                }
            }
            if (mutableListOfFoods.isNotEmpty()) {
                send(State.success(mutableListOfFoods.toList()))
            } else {
                send(State.failed("Empty List"))
            }
        }.catch {
            send(State.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO).collect()

    }
}