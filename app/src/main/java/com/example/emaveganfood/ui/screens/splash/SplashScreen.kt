package com.example.emaveganfood.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.emaveganfood.VeganScreen

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
            onNavigateToNextScreen(VeganScreen.Account.name)
        } else {
            onNavigateToNextScreen(VeganScreen.Login.name)
        }
    }
}