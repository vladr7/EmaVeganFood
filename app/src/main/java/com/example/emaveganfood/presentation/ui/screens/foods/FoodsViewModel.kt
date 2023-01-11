package com.example.emaveganfood.presentation.ui.screens.foods

import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsWithImagesCombinedUseCase
import com.example.emaveganfood.core.utils.State
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
class FoodsViewModel @Inject constructor(
    private val getAllFoodsWithImagesCombinedUseCase: GetAllFoodsWithImagesCombinedUseCase,
    private val foodMapper: FoodMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow<FoodsViewState>(FoodsViewState())
    val state: StateFlow<FoodsViewState> = _state

    init {
        getFoodsAndImages()
    }

    private fun getFoodsAndImages() {
        viewModelScope.launch {
            getAllFoodsWithImagesCombinedUseCase().collectLatest { state ->
                when (state) {
                    is State.Failed -> {
                        showError(errorMessage = state.message)
                    }
                    is State.Loading -> {
                        showLoading()
                    }
                    is State.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                listAllFoods = state.data.map { food ->
                                    foodMapper.mapToViewData(food)
                                }
                            )
                        }
                    }
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

data class FoodsViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val listAllFoods: List<FoodViewData> = emptyList(),
) : ViewState(isLoading, errorMessage)