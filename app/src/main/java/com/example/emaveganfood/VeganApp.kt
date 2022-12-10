package com.example.emaveganfood

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
import com.example.emaveganfood.ui.screens.*
import com.example.emaveganfood.ui.theme.Primary
import com.example.emaveganfood.ui.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
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
    val currentScreen = when (backStackEntry?.destination?.route) {
        NavigationItem.Login.route -> {
            bottomBarState.value = false
            NavigationItem.Login
        }
        NavigationItem.Account.route -> {
            bottomBarState.value = true
            NavigationItem.Account
        }
        NavigationItem.Foods.route -> {
            bottomBarState.value = true
            NavigationItem.Foods
        }
        NavigationItem.Favorites.route -> {
            bottomBarState.value = true
            NavigationItem.Favorites
        }
        NavigationItem.Generate.route -> {
            bottomBarState.value = true
            NavigationItem.Generate
        }
        NavigationItem.AddFood.route -> {
            bottomBarState.value = true
            NavigationItem.AddFood
        }
        else -> {
            bottomBarState.value = true
            NavigationItem.Account
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
                    },
                    username = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
                )
            }
            composable(route = NavigationItem.Favorites.route) {
                FavoritesScreen()
            }
            composable(route = NavigationItem.Foods.route) {
                FoodsScreen(
                    onAddFoodClicked = {
                        navController.navigate(NavigationItem.AddFood.route)
                    })
            }
            composable(route = NavigationItem.Generate.route) {
                GenerateScreen()
            }
            composable(route = NavigationItem.AddFood.route) {
                AddFoodScreen()
            }
        }
    }
}

fun getStartDestination(mainUiState: MainUiState): String =
    if (mainUiState.isLoggedIn) NavigationItem.Foods.route else NavigationItem.Login.route


@Composable
fun TopBar(
    currentScreen: NavigationItem,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                stringResource(id = currentScreen.title),
                color = Color.White
            )
        },
        modifier = Modifier,
        backgroundColor = Primary
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
            BottomNavigation(
                backgroundColor = colorResource(id = R.color.primary),
            ) {
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