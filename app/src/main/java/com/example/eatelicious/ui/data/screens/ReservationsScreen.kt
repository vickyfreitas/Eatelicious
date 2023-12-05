package com.example.eatelicious.ui.data.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eatelicious.data.ReservationsViewModel
import com.example.eatelicious.models.Reservation
import com.example.eatelicious.utils.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ReservationsScreen(viewModel: ReservationsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserReservations()
    }

    val reservations = remember {
        mutableStateListOf<Reservation>()
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
        is DataState.SuccessReservations -> {
            reservations.clear()
            reservations.addAll(result.data)

            ReservationList(viewModel, reservations)
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
fun ReservationList(
    viewModel: ReservationsViewModel,
    reservations: SnapshotStateList<Reservation>
) {
    val scope = rememberCoroutineScope()

    LazyColumn {
        items(reservations) { reservation ->
            ReservationItem(
                reservation = reservation,
                coroutineScope = scope,
                onDeleteReservation = {deletedReservation ->
                    scope.launch {
                        viewModel.deleteReservation(deletedReservation)
                    }
                    val filteredReservations = reservations.filterNot {
                            r: Reservation -> r.id == deletedReservation.id
                    }

                    reservations.clear()
                    reservations.addAll(filteredReservations)
                }
            )
        }
    }
}


@Composable
fun ReservationItem(
    reservation: Reservation,
    coroutineScope: CoroutineScope,
    onDeleteReservation: (Reservation) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Name: ${reservation.name}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Number of guests: ${reservation.numberOfPeople}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onDeleteReservation(reservation)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colors.error
                )
            }
        }
    }
}
