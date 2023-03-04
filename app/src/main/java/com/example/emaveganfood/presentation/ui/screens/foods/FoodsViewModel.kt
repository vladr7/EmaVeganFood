package com.example.emaveganfood.presentation.ui.screens.foods

import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.domain.repository.NetworkConnectionManager
import com.example.emaveganfood.domain.repository.FoodRepository
import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.example.emaveganfood.presentation.models.FoodMapper
import com.example.emaveganfood.presentation.models.FoodViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val foodMapper: FoodMapper,
    private val networkConnectionManager: NetworkConnectionManager,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<FoodsViewState>(FoodsViewState())
    val state: StateFlow<FoodsViewState> = _state

    init {
        getNetworkStatus()
        refreshDataFromRepository()
        getFoodsAndImagesNew()
    }

    private fun getFoodsAndImagesNew() {
        viewModelScope.launch {
            foodRepository.foods.collectLatest { newList ->
                _state.update {
                    it.copy(
                        listAllFoods = newList.map { food ->
                            foodMapper.mapToViewData(food)
                        }
                    )
                }
            }
        }
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            foodRepository.refreshFoods()
        }
    }

    private fun getNetworkStatus() {
        networkConnectionManager.startListenNetworkState()
        viewModelScope.launch {
            networkConnectionManager.isNetworkConnectedFlow.debounce(2000L).collectLatest { networkStatus ->
                _state.update {
                    it.copy(
                        isNetworkAvailable = networkStatus
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

data class FoodsViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val listAllFoods: List<FoodViewData> = emptyList(),
    val isNetworkAvailable: Boolean? = null,
) : ViewState(isLoading, errorMessage)