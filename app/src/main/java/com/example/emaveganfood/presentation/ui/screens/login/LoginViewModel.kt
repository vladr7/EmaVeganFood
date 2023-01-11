package com.example.emaveganfood.presentation.ui.screens.login

import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: BaseViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow<LoginViewState>(LoginViewState())
    val state: StateFlow<LoginViewState> = _state

    fun signIn(email: String, displayName: String, idToken: String) {
        if(idToken.isEmpty()) return

        firebaseAuthWithGoogle(idToken)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    println("SignIn success! ${task.result.user}")
                    _state.update {
                        it.copy(
                            shouldNavigate = true
                        )
                    }
                } else {
                    println("SignIn failed: ${task.exception?.localizedMessage}")
                }
            }
    }
}

data class LoginViewState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val user: User? = null,
    val shouldNavigate: Boolean? = null
): ViewState(isLoading, errorMessage)