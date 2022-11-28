package com.example.emaveganfood.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.emaveganfood.data.MainUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(MainUiState(
        isLoggedIn = firebaseAuth.currentUser != null,
        signInSuccess = false
    ))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

}