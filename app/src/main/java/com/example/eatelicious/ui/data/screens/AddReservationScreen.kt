package com.example.eatelicious.ui.data.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.eatelicious.data.ReservationsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReservationScreen(
    viewModel: ReservationsViewModel,
    currentUserEmail: String,
    restaurantId: String,
    restaurantName: String,
    onNavigateToReservations: () -> Unit
) {

    var numberOfPeople by remember { mutableStateOf(0) }
    val currentContext = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = restaurantName,
            onValueChange = { },
            label = { Text("Restaurant Name") },
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = if (numberOfPeople > 0) numberOfPeople.toString()  else "",
            onValueChange = { value ->
                numberOfPeople = value.toIntOrNull() ?: 0
            },
            label = { Text("Number of Guests") },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addReservation(
                    currentUserEmail, restaurantId, restaurantName, numberOfPeople)

                CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
                    Toast.makeText(
                        currentContext,
                        "Reserved $restaurantName for $numberOfPeople guests!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                onNavigateToReservations()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Reserve")
        }
    }
}
