package com.example.emaveganfood.data.repositories.foodrepository

import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.utils.State
import kotlinx.coroutines.flow.Flow

interface IFoodRepository {

    fun addFood(food: Food): Flow<State<Food>>
}