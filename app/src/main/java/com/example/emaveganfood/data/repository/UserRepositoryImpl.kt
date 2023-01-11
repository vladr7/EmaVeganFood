package com.example.emaveganfood.data.repository

import com.example.emaveganfood.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth

class UserRepositoryImpl: IUserRepository {

    override fun isUserLoggedIn() = FirebaseAuth.getInstance().currentUser != null
}