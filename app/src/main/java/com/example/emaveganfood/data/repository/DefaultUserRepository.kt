package com.example.emaveganfood.data.repository

import com.example.emaveganfood.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

class DefaultUserRepository: UserRepository {

    override fun isUserLoggedIn() = FirebaseAuth.getInstance().currentUser != null
}