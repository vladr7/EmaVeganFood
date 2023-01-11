package com.example.emaveganfood.presentation.ui.screens.foods

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.emaveganfood.R
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.presentation.models.FoodViewData
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
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val listState = rememberLazyListState()
    val fabVisibility by derivedStateOf {
        listState.firstVisibleItemIndex == 0
    }

    Scaffold(
        floatingActionButton = {
            AddFoodFab(
                isVisibleBecauseOfScrolling = fabVisibility,
                onAddFoodClicked = onAddFoodClicked
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(150.dp)
            )
        }
        if (state.errorMessage != null) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
        // todo show a textview instead of toast
        if(state.isNetworkAvailable == false) {
            Toast.makeText(context, "Network not available!", Toast.LENGTH_LONG).show()
        }
        LazyColumn(
            state = listState
        ) {
            items(state.listAllFoods) { food ->
                FoodItem(food = food)
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
                Text(text = stringResource(id = R.string.addfood_title))
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
    food: FoodViewData,
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
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp)
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.imageRef)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(100.dp)
                    )
                }
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