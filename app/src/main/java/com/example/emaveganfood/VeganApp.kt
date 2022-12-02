package com.example.emaveganfood

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emaveganfood.data.DataSource
import com.example.emaveganfood.data.MainUiState
import com.example.emaveganfood.navigation.NavigationItem
import com.example.emaveganfood.ui.screens.account.AccountScreen
import com.example.emaveganfood.ui.screens.FavoritesScreen
import com.example.emaveganfood.ui.screens.FoodsScreen
import com.example.emaveganfood.ui.screens.GenerateScreen
import com.example.emaveganfood.ui.screens.LoginScreen
import com.example.emaveganfood.ui.viewmodels.LoginViewModel
import com.example.emaveganfood.ui.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class
)
@Composable
fun VeganApp(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: NavigationItem.Account.route

    when (backStackEntry?.destination?.route) {
        NavigationItem.Login.route -> {
            bottomBarState.value = false
        }
        NavigationItem.Account.route -> {
            bottomBarState.value = true
        }
        NavigationItem.Foods.route -> {
            bottomBarState.value = true
        }
        NavigationItem.Favorites.route -> {
            bottomBarState.value = true
        }
        NavigationItem.Generate.route -> {
            bottomBarState.value = true
        }

    }

    Scaffold(
        topBar = {
            TopBar(currentScreen = currentScreen)
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarState = bottomBarState
            )
        }
    ) { innerPadding ->
        val mainUiState by mainViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = getStartDestination(mainUiState),
            modifier = modifier.padding(innerPadding)
        ) {

            composable(route = NavigationItem.Login.route) {
                LoginScreen(
                    onSuccesLogin = {
                        navController.navigate(NavigationItem.Foods.route) {
                            popUpTo(NavigationItem.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = NavigationItem.Account.route) {
                AccountScreen(
                    onLogoutButtonClicked = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(NavigationItem.Login.route)
                    }
                )
            }
            composable(route = NavigationItem.Favorites.route) {
                FavoritesScreen()
            }
            composable(route = NavigationItem.Foods.route) {
                FoodsScreen(DataSource.loadFoods())
            }
            composable(route = NavigationItem.Generate.route) {
                GenerateScreen()
            }
        }
    }
}

fun getStartDestination(mainUiState: MainUiState): String =
    if (mainUiState.isLoggedIn) NavigationItem.Foods.route else NavigationItem.Login.route


@Composable
fun TopBar(
    currentScreen: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen) },
        modifier = Modifier,
    )
}

@Composable
fun BottomBar(navController: NavController, bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        NavigationItem.Account,
        NavigationItem.Foods,
//        NavigationItem.Favorites,
//        NavigationItem.Generate
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = { Text(text = stringResource(id = item.title)) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}