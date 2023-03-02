package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.domain.repository.FoodRepository
import com.example.emaveganfood.domain.repository.NewFoodRepository
import javax.inject.Inject

class GetAllFoodsUseCase @Inject constructor(
    private val foodsRepository: NewFoodRepository
) {

    operator fun invoke() =
        foodsRepository.foods
}