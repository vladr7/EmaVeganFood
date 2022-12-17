package com.example.emaveganfood.presentation.ui.screens.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsWithImagesCombinedUseCase
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.core.utils.State
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
) : ViewModel() {

    private val _allFoodsStateFlow = MutableStateFlow<State<List<FoodViewData>>>(State.loading())
    val allFoodsStateFlow: StateFlow<State<List<FoodViewData>>> = _allFoodsStateFlow

    init {
        getFoodsAndImages()
    }

    private fun getFoodsAndImages() {
        viewModelScope.launch {
            getAllFoodsWithImagesCombinedUseCase().collectLatest { state ->
                when (state) {
                    is State.Failed -> {
                        _allFoodsStateFlow.update { State.failed(state.message) }
                    }
                    is State.Loading -> {
                        _allFoodsStateFlow.update { State.loading() }
                    }
                    is State.Success -> {
                        _allFoodsStateFlow.update {
                            State.success(
                                state.data.map { food ->
                                    foodMapper.mapToViewData(food)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}