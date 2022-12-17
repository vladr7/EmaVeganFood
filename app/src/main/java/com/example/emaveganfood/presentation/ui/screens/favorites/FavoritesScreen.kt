package com.example.emaveganfood.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.emaveganfood.R

@Composable
fun FavoritesScreen() {

    Column {
        Text(text = stringResource(id = R.string.favorites_title))
    }
}