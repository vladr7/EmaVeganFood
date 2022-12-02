package com.example.emaveganfood.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.emaveganfood.R

@Composable
fun AccountScreen(
    onLogoutButtonClicked: () -> Unit
) {

    Column {
        Text(text = stringResource(id = R.string.account_title))

        Button(onClick = onLogoutButtonClicked) {
            Text(text = "Logout")
        }
    }
}