package com.example.emaveganfood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.emaveganfood.R
import com.example.emaveganfood.ui.models.User

@Composable
fun AccountScreen(
    onLogoutButtonClicked: () -> Unit,
    username: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Salut ${username.uppercase()}!",
            Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp),
            style = MaterialTheme.typography.h5,
        )

        Button(onClick = onLogoutButtonClicked) {
            Text(text = "Logout")
        }
    }
}