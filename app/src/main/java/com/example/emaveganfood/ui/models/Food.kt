package com.example.emaveganfood.ui.models

import androidx.annotation.DrawableRes

data class Food(
    val name: String = "",
    val description: String = "",
    @DrawableRes val imageResourceId: Int
)
