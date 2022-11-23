package com.example.emaveganfood

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emaveganfood.ui.screens.account.AccountScreen
import com.example.emaveganfood.ui.screens.login.LoginScreen
import com.example.emaveganfood.ui.screens.login.LoginViewModel
import com.example.emaveganfood.ui.screens.splash.SplashScreen
import com.example.emaveganfood.ui.screens.splash.SplashViewModel

enum class VeganScreen(@StringRes val title: Int) {
    Splash(title = R.string.splash_screen_name),
    Login(title = R.string.login_screen_name),
    Account(title = R.string.account_screen_name),
    Foods(title = R.string.foods_screen_name),
    Favorites(title = R.string.favorites_screen_name),
    Generate(title = R.string.generate_screen_name),
}

@Composable
fun VeganAppBar(
    currentScreen: VeganScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = Modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun VeganApp(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = VeganScreen.valueOf(
        backStackEntry?.destination?.route ?: VeganScreen.Login.name
    )

    Scaffold(
        topBar = {
            VeganAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        val splashUiState by splashViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = VeganScreen.Splash.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = VeganScreen.Splash.name) {
                SplashScreen(
                    isUserLoggedIn = splashUiState.isUserLoggedIn,
                    onNavigateToNextScreen = { nextScreen -> navController.navigate(nextScreen) }
                )
            }
            composable(route = VeganScreen.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = { navController.navigate(VeganScreen.Account.name) }
                )
            }
            composable(route = VeganScreen.Account.name) {
                AccountScreen()
            }
        }
    }

}