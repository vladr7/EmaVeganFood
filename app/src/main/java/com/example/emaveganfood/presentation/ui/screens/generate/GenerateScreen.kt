package com.example.emaveganfood.presentation.ui.screens.generate

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.emaveganfood.R
import com.example.emaveganfood.presentation.models.FoodViewData
import com.example.emaveganfood.presentation.ui.screens.foods.FoodDescription

@Preview
@Composable
fun GenerateScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoodItem(food = state.food)

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Generat", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Genereaza")
        }
    }

}

@Composable
fun FoodItem(
    food: FoodViewData,
    modifier: Modifier = Modifier
) {
    val color by animateColorAsState(
        targetValue = colorResource(id = R.color.light_purple)
    )

    val itemPadding = 8.dp

    Card(
        modifier = Modifier
            .padding(itemPadding)
            .clickable {},
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
            FoodDescription(description = food.description)
        }
    }
}