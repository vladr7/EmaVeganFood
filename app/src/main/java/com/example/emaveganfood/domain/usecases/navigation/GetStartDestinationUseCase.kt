package com.example.emaveganfood.domain.usecases.navigation

import com.example.emaveganfood.core.navigation.NavigationItem
import com.example.emaveganfood.domain.repository.UserRepository
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): String {
        return if (userRepository.isUserLoggedIn()) NavigationItem.Foods.route else NavigationItem.Login.route
    }
}