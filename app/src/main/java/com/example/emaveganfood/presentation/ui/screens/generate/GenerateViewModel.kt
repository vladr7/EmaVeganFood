package com.example.emaveganfood.presentation.ui.screens.generate

import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.example.emaveganfood.presentation.models.FoodViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(

): BaseViewModel() {

    private val _state = MutableStateFlow<GenerateViewState>(GenerateViewState())
    val state: StateFlow<GenerateViewState> = _state

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