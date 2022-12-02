package com.example.emaveganfood.navigation

import androidx.annotation.StringRes
import com.example.emaveganfood.R

sealed class NavigationItem(var route: String, var icon: Int, @StringRes val title: Int) {
    object Account : NavigationItem("account", R.drawable.ic_baseline_person_24, R.string.account_screen_name)
    object Foods : NavigationItem("foods", R.drawable.ic_baseline_list_24, R.string.foods_screen_name)
    object Favorites : NavigationItem("favorites", R.drawable.car_icon, R.string.favorites_screen_name)
    object Generate : NavigationItem("generate", R.drawable.car_icon, R.string.generate_screen_name)
    object Login : NavigationItem("login", R.drawable.car_icon, R.string.login_screen_name)
    object AddFood : NavigationItem("addfood", R.drawable.car_icon, R.string.addfood_screen_name)
}
