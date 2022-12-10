package com.example.emaveganfood.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.emaveganfood.R
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.ui.viewmodels.FoodsViewModel
import com.example.emaveganfood.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun FoodsScreen(
    modifier: Modifier = Modifier,
    onAddFoodClicked: () -> Unit,
    viewModel: FoodsViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    val fabVisibility by derivedStateOf {
        listState.firstVisibleItemIndex == 0
    }
    var allFoods by remember {
        mutableStateOf(listOf<Food>())
    }

    getAllFoods(coroutineScope, viewModel, onFoodsListChanged = {
        allFoods = it
    })

    Scaffold(
        floatingActionButton = {
            AddFoodFab(
                isVisibleBecauseOfScrolling = fabVisibility,
                onAddFoodClicked = onAddFoodClicked
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            state = listState
        ) {
            items(allFoods) { food ->
                FoodItem(food = food)
            }
        }

    }
}

fun getAllFoods(
    coroutineScope: CoroutineScope,
    viewModel: FoodsViewModel,
    onFoodsListChanged: (List<Food>) -> Unit,
) {
    coroutineScope.launch {
        viewModel.getAllFoods().collectLatest {
            when (it) {
                is State.Failed -> {
                    println()
                }
                is State.Loading -> {
                    println()
                }
                is State.Success -> {
                    onFoodsListChanged(it.data)
                }
            }
        }
    }
}

@Composable
fun AddFoodFab(
    modifier: Modifier = Modifier,
    isVisibleBecauseOfScrolling: Boolean,
    onAddFoodClicked: () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(16.dp),
            text = {
                Text(text = "Add Food")
            },
            onClick = onAddFoodClicked,
            icon = {
                Icon(Icons.Filled.Add, null)
            },
        )
    }
}


@Composable
fun FoodItem(
    food: Food,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (expanded) colorResource(id = R.color.light_purple) else MaterialTheme.colors.surface,
    )

    val itemPadding = if (expanded) 8.dp else 16.dp

    Card(
        modifier = Modifier
            .padding(itemPadding)
            .clickable {
                expanded = !expanded
            },
        elevation = 8.dp, shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .background(color)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_no_photography_24),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = food.title,
                Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.h5,
            )
            if (expanded) {
                FoodDescription(description = food.description)
            }
        }
    }
}

@Composable
fun FoodDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.descriere),
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
        )
    }
}