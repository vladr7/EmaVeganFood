package com.example.emaveganfood.ui.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emaveganfood.R
import com.example.emaveganfood.utils.AuthResultContract
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit = {},
    loginViewModel: LoginViewModel = viewModel(),
    onSuccesLogin: () -> Unit = {},
) {

    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(loginViewModel) { loginViewModel.user }.collectAsState()
    val shouldNavigate by remember(loginViewModel) { loginViewModel.shouldNavigate }.collectAsState()
    var navigated by remember { mutableStateOf<Boolean>(false) }
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        loginViewModel.signIn(
                            email = account.email.toString(),
                            displayName = account.displayName.toString(),
                            idToken = account.idToken ?: ""
                        )
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }

    if(shouldNavigate == true && !navigated) {
        navigated = true
        onSuccesLogin()
    }

    Column {
        Text(text = stringResource(id = R.string.welcome_message))

        Spacer(modifier = Modifier.height(300.dp))

        Button(
            onClick = {
                authResultLauncher.launch(signInRequestCode)
            },
        ) {
            Text(stringResource(id = R.string.login_button))
        }
    }

}