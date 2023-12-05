package com.example.eatelicious.models

data class Reservation(
    var id: String = "",
    var restaurantId: String = "",
    var name: String = "",
    var numberOfPeople: Int = 0,
)
