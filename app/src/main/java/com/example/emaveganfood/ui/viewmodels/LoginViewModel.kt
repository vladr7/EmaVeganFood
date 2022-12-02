package com.example.emaveganfood.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.emaveganfood.ui.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user
    
    private val _shouldNavigate: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val shouldNavigate: StateFlow<Boolean?> = _shouldNavigate

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
                    _user.value = User(email, displayName)
                    _shouldNavigate.value = true
                } else {
                    println("SignIn failed: ${task.exception?.localizedMessage}")
                }
            }
    }
}