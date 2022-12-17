package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.domain.repository.IFoodRepository
import javax.inject.Inject

class GetAllFoodImagesUseCase @Inject constructor(
    private val foodsRepository: IFoodRepository
) {

    operator fun invoke() = foodsRepository.getAllFoodImages()
}