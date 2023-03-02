package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class GenerateFoodUseCase @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) {
    operator fun invoke(): Flow<Food> =
        getAllFoodsUseCase().map { foods ->
            val randomIndex = Random.nextInt(from = 0, until = foods.size)
            foods[randomIndex]
        }
}