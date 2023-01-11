package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.core.utils.State
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllFoodsWithImagesCombinedUseCase @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val getAllFoodImagesUseCase: GetAllFoodImagesUseCase
) {

    suspend operator fun invoke() = channelFlow<State<List<Food>>> {
        send(State.loading())

        getAllFoodsUseCase().collectLatest { listState -> // todo this should be reactive
            when(val result = listState) {
                is State.Success -> {
                    getAllFoodImagesUseCase().collectLatest { images ->
                        val mutableListOfFoods = mutableListOf<Food>()
                        result.data.forEach { food ->
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
                    }
                }
                is State.Failed -> {
                    send(State.failed(result.message))
                }
                is State.Loading -> {

                }
            }
        }
    }
}