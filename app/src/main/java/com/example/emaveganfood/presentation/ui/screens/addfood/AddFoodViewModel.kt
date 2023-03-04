package com.example.emaveganfood.presentation.ui.screens.addfood

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.domain.repository.FoodRepository
import com.example.emaveganfood.domain.usecases.foods.AddFoodUseCase
import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val addFoodUseCase: AddFoodUseCase,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<AddFoodViewState>(AddFoodViewState())
    val state: StateFlow<AddFoodViewState> = _state

    fun updateFoodTitle(title: String) {
        _state.update {
            it.copy(foodTitle = title, foodItem = it.foodItem.copy(title = title))
        }
    }

    fun updateFoodDescription(description: String) {
        _state.update {
            it.copy(
                foodDescription = description,
                foodItem = it.foodItem.copy(description = description)
            )
        }
    }

    fun updateImageUri(imageUri: Uri?) {
        _state.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun updateHasImage(hasImage: Boolean) {
        _state.update {
            it.copy(hasImage = hasImage)
        }
    }

    fun addFoodAndImage() {
        viewModelScope.launch {
            showLoading()
            when (val state = addFoodUseCase.execute(
                food = state.value.foodItem,
                imageUri = state.value.imageUri
            )) {
                is State.Failed -> {
                    showError(errorMessage = state.message)
                }
                is State.Loading -> {
                    showLoading()
                }
                is State.Success -> {
                    refreshDataFromRepository()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                        )
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

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            foodRepository.refreshFoods()
        }
    }
}

data class AddFoodViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val foodTitle: String = "",
    val foodDescription: String = "",
    val foodItem: Food = Food(id = UUID.randomUUID().toString()),
    val hasImage: Boolean = false,
    val imageUri: Uri? = null
) : ViewState(isLoading, errorMessage)