package com.example.emaveganfood.presentation.ui

import androidx.lifecycle.ViewModel
import com.example.emaveganfood.domain.usecases.navigation.GetStartDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStartDestinationUseCase: GetStartDestinationUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow(getStartDestinationUseCase())
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

}