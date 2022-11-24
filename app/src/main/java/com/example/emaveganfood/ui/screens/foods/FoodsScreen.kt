package com.example.emaveganfood.ui.screens.foods

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.emaveganfood.R
import com.example.emaveganfood.ui.models.Food

@Composable
fun FoodsScreen(
    allFoods: List<Food>,
    modifier: Modifier = Modifier
) {

    Column {
        Text(text = stringResource(id = R.string.foods_title))
        
        LazyColumn {
            items(allFoods) { food ->
                FoodCard(food = food)
            }
        }
    }
}

@Composable
fun FoodCard(
    food: Food,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column {
//            Image(
//                painter = painterResource(affirmation.imageResourceId),
//                contentDescription = stringResource(affirmation.stringResourceId),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(194.dp),
//                contentScale = ContentScale.Crop
//            )
            Text(
                text = food.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h2
            )
            Text(
                text = food.description,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}