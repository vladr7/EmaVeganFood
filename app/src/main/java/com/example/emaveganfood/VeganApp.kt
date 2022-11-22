package com.example.emaveganfood

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.emaveganfood.ui.SplashScreen

enum class CupcakeScreen() {
    Splash,
    Login,
    Account,
    Foods,
    Favorites,
    Generate
}

@Composable
fun VeganApp(
    modifier: Modifier = Modifier
) {
    SplashScreen()
}