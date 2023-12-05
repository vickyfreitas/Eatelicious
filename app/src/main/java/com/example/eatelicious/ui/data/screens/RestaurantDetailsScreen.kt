package com.example.eatelicious.ui.data.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eatelicious.data.RestaurantsViewModel
import com.example.eatelicious.models.Restaurant
import com.example.eatelicious.utils.DataState

@Composable
fun RestaurantDetailsScreen(
    viewModel: RestaurantsViewModel,
    restaurantId: String,
    onNavigateToReserveTable: (String, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData(restaurantId)
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
        is DataState.SuccessRestaurant -> {
            RestaurantInfo(result.data, onNavigateToReserveTable)
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
fun RestaurantInfo(
    restaurant: Restaurant,
    onNavigateToReserveTable: (String, String) -> Unit
) {
    Column(
        Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(restaurant.image)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "restaurant",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(0.3f),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = restaurant.name,
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        onNavigateToReserveTable(restaurant.id, restaurant.name)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Reserve Table")
                }
            }
        }
    }
}
