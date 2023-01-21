package com.example.emaveganfood.domain.repository

interface UserRepository {

    fun isUserLoggedIn(): Boolean
}