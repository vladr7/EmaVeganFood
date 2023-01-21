package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.domain.repository.FoodRepository
import javax.inject.Inject

class GetAllFoodImagesUseCase @Inject constructor(
    private val foodsRepository: FoodRepository
) {

    operator fun invoke() = foodsRepository.getAllFoodImages()
}