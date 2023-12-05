package com.example.eatelicious.utils

import com.example.eatelicious.models.Reservation
import com.example.eatelicious.models.Restaurant

sealed class DataState {
    class SuccessRestaurant(
        val data: Restaurant) : DataState()
    class SuccessRestaurants(
        val data: MutableList<Restaurant>) : DataState()

    class SuccessReservations(
        val data: MutableList<Reservation>) : DataState()

    class Failure(val message: String) : DataState()

    object Loading : DataState()

    object Empty : DataState()
}
