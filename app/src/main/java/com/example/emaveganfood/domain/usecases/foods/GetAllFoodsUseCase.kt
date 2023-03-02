package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.domain.repository.FoodRepository
import javax.inject.Inject

class GetAllFoodsUseCase @Inject constructor(
    private val foodsRepository: FoodRepository
) {

    operator fun invoke() = emptyList<Food>()
//        foodsRepository.getAllFoods()
}