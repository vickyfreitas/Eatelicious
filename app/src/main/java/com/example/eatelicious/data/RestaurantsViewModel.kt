package com.example.eatelicious.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eatelicious.utils.DataState
import com.example.eatelicious.models.Restaurant
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val RESTAURANTS_COLLECTION = "restaurants"

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val db: FirebaseFirestore
): ViewModel() {

    var response: DataState by mutableStateOf(DataState.Empty)
        private set

    fun fetchData() {

        if (response !is DataState.Loading) {
            response = DataState.Loading

            db.collection(RESTAURANTS_COLLECTION)
                .get()
                .addOnSuccessListener { result ->
                    val restaurants = mutableListOf<Restaurant>()
                    for (document in result) {
                        val restaurant = document.toObject(Restaurant::class.java)

                        restaurant.id = document.id
                        restaurants.add(restaurant)
                    }

                    response = DataState.SuccessRestaurants(restaurants)
                }
                .addOnFailureListener { exception ->
                    response = exception.message?.let { DataState.Failure(it) }!!
                }
        }
    }

    fun fetchData(id: String) {
        if (response !is DataState.Loading) {
            response = DataState.Loading

            db.collection(RESTAURANTS_COLLECTION)
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val restaurant = document.toObject(Restaurant::class.java)
                        restaurant?.id = document.id

                        response = DataState.SuccessRestaurant(restaurant!!)
                    } else {
                        response = DataState.Failure("Restaurant not found")
                    }
                }
                .addOnFailureListener { exception ->
                    response = exception.message?.let { DataState.Failure(it) }!!
                }
        }
    }

}
