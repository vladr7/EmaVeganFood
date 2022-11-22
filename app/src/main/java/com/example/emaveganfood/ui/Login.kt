package com.example.emaveganfood.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.emaveganfood.R

@Composable
fun LoginScreen() {

    Column {
        Text(text = stringResource(id = R.string.welcome_message))

        Spacer(modifier = Modifier.height(300.dp))

        Button(
            onClick = { },
        ) {
            Text(stringResource(id = R.string.login_button))
        }
    }

}