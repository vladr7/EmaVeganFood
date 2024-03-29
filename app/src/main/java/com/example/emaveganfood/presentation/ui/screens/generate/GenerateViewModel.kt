package com.example.emaveganfood.presentation.ui.screens.generate

import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.domain.usecases.foods.GenerateFoodUseCase
import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.example.emaveganfood.presentation.models.FoodMapper
import com.example.emaveganfood.presentation.models.FoodViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    private val generateFoodUseCase: GenerateFoodUseCase,
    private val foodMapper: FoodMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow<GenerateViewState>(GenerateViewState())
    val state: StateFlow<GenerateViewState> = _state

    fun generateFoodEvent() {
        viewModelScope.launch {
            showLoading()
            generateFoodUseCase().collectLatest { food ->
                hideLoading()
                _state.update {
                    it.copy(
                        food = foodMapper.mapToViewData(food)
                    )
                }
            }
        }
    }

    override fun showError(errorMessage: String) {
        _state.update {
            it.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    override fun hideError() {
        _state.update {
            it.copy(errorMessage = null, isLoading = false)
        }
    }

    override fun showLoading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    override fun hideLoading() {
        _state.update {
            it.copy(isLoading = false)
        }
    }
}

data class GenerateViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val food: FoodViewData = FoodViewData(),
    val isNetworkAvailable: Boolean? = null,
) : ViewState(isLoading, errorMessage)