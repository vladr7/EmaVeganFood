package com.example.emaveganfood.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.emaveganfood.navigation.NavigationItem

@Composable
fun SplashScreen(
    isUserLoggedIn: Boolean,
    onNavigateToNextScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.background(Color.Green)
    ) {
        if(isUserLoggedIn) {
            onNavigateToNextScreen(NavigationItem.Account.route)
        } else {
            onNavigateToNextScreen(NavigationItem.Login.route)
        }
    }
}