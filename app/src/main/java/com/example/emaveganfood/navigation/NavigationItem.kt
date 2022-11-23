package com.example.emaveganfood.navigation

import androidx.annotation.StringRes
import com.example.emaveganfood.R

sealed class NavigationItem(var route: String, var icon: Int, @StringRes val title: Int) {
    object Account : NavigationItem("account", R.drawable.car_icon, R.string.account_screen_name)
    object Foods : NavigationItem("foods", R.drawable.car_icon, R.string.foods_screen_name)
    object Favorites : NavigationItem("favorites", R.drawable.car_icon, R.string.favorites_screen_name)
    object Generate : NavigationItem("generate", R.drawable.car_icon, R.string.generate_screen_name)
    object Login : NavigationItem("login", R.drawable.car_icon, R.string.login_screen_name)
    object Splash : NavigationItem("splash", R.drawable.car_icon, R.string.splash_screen_name)
}
