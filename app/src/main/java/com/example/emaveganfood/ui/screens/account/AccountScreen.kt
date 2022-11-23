package com.example.emaveganfood.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.emaveganfood.R

@Composable
fun AccountScreen() {

    Column {
        Text(text = stringResource(id = R.string.account_title))
    }
}