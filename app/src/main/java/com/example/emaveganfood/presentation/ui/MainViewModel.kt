package com.example.emaveganfood.presentation.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(
        MainUiState(
        isLoggedIn = firebaseAuth.currentUser != null,
    )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

}