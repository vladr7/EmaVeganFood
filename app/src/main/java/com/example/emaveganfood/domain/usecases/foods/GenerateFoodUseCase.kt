package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class GenerateFoodUseCase @Inject constructor(
    private val getAllFoodsWithImagesCombinedUseCase: GetAllFoodsWithImagesCombinedUseCase
) {
    suspend operator fun invoke(): Flow<State<Food>> =
        getAllFoodsWithImagesCombinedUseCase().map { listState ->
            when(listState) {
                is State.Failed -> {
                    State.failed<Food>(message = listState.message)
                }
                is State.Loading -> {
                    State.loading<Food>()
                }
                is State.Success -> {
                    val randomIndex = Random.nextInt(from = 0, until = listState.data.size)
                    State.success(data = listState.data[randomIndex])
                }
            }
        }
}