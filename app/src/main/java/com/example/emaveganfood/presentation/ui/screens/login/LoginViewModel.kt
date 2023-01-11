package com.example.emaveganfood.presentation.ui.screens.login

import com.example.emaveganfood.presentation.base.BaseViewModel
import com.example.emaveganfood.presentation.base.ViewState
import com.example.emaveganfood.presentation.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: BaseViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

//    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
//    val user: StateFlow<User?> = _user
//
//    private val _shouldNavigate: MutableStateFlow<Boolean?> = MutableStateFlow(null)
//    val shouldNavigate: StateFlow<Boolean?> = _shouldNavigate

    private val _state = MutableStateFlow<LoginViewState>(LoginViewState())
    val state: StateFlow<LoginViewState> = _state

    suspend fun signIn(email: String, displayName: String, idToken: String) {
        if(idToken.isEmpty()) return

        firebaseAuthWithGoogle(email, displayName, idToken)
    }

    private fun firebaseAuthWithGoogle(email: String, displayName: String, idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    println("SignIn success! ${task.result.user}")
                    _state.update {
                        it.copy(
                            user = User(email, displayName),
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