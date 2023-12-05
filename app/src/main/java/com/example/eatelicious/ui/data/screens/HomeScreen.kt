package com.example.eatelicious.ui.data.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eatelicious.utils.DataState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter
import com.example.eatelicious.data.RestaurantsViewModel

import com.example.eatelicious.models.Restaurant

@Composable
fun HomeScreen(
    viewModel: RestaurantsViewModel,
    onNavigateToRestaurantDetails: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    when (val result = viewModel.response) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DataState.SuccessRestaurants -> {
            RestaurantList(result.data, onNavigateToRestaurantDetails)
        }
        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Looking for data",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
    }
}

@Composable
fun RestaurantList(restaurants: List<Restaurant>, onNavigateToRestaurantDetails: (String) -> Unit) {
    LazyColumn {
        items(restaurants) { restaurant ->
            CardItem(restaurant, onNavigateToRestaurantDetails)
        }
    }
}


@Composable
fun CardItem(restaurant: Restaurant, onNavigateToRestaurantDetails: (String) -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onNavigateToRestaurantDetails(restaurant.id)
            }
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(restaurant.image),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Restaurant image",
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = restaurant.name,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}