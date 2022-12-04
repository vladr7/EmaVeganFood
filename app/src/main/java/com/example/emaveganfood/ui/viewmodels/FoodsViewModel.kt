package com.example.emaveganfood.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.emaveganfood.data.repositories.foodrepository.IFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val foodsRepository: IFoodRepository
): ViewModel() {

    fun getAllFoods() = foodsRepository.getAllFoods()
}