package com.example.emaveganfood.domain.usecases.foods

import com.example.emaveganfood.data.models.Food
import javax.inject.Inject
import kotlin.random.Random

class GenerateFoodUseCase @Inject constructor(
) {

    operator fun invoke(): Food {
        return Food(
            description = Random.nextInt().toString()
        )
    }
}