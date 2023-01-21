package com.example.emaveganfood.presentation.ui.screens.generate

import com.example.emaveganfood.domain.usecases.foods.GenerateFoodUseCase
import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.example.emaveganfood.presentation.models.FoodMapper
import com.example.emaveganfood.presentation.models.FoodViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    private val generateFoodUseCase: GenerateFoodUseCase,
    private val foodMapper: FoodMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow<GenerateViewState>(GenerateViewState())
    val state: StateFlow<GenerateViewState> = _state

    fun generateFoodEvent() {
        val food = generateFoodUseCase()
        _state.update {
            it.copy(
                food = foodMapper.mapToViewData(food)
            )
        }
    }
}

data class GenerateViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val food: FoodViewData = FoodViewData(
        title = "abc",
        description = "bcd",
        imageRef = ""
    ),
    val isNetworkAvailable: Boolean? = null,
) : ViewState(isLoading, errorMessage)