package com.example.emaveganfood.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.domain.usecases.foods.GetAllFoodsWithImagesCombinedUseCase
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val getAllFoodsWithImagesCombinedUseCase: GetAllFoodsWithImagesCombinedUseCase
): ViewModel() {

    private val _allFoodsStateFlow = MutableStateFlow<State<List<Food>>>(State.loading())
    val allFoodsStateFlow: StateFlow<State<List<Food>>> = _allFoodsStateFlow

    fun refreshAllFoodsList() {
        viewModelScope.launch {
            getAllFoodsWithImagesCombinedUseCase().collectLatest { state ->
                when(state) {
                    is State.Failed -> {
                        _allFoodsStateFlow.update { State.failed(state.message) }
                    }
                    is State.Loading -> {
                        _allFoodsStateFlow.update { State.loading() }
                    }
                    is State.Success -> {
                        _allFoodsStateFlow.update { State.success(state.data) }
                    }
                }
            }
        }
    }
}