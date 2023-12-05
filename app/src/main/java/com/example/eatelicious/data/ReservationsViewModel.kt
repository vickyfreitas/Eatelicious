package com.example.eatelicious.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eatelicious.models.Reservation
import com.example.eatelicious.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val RESERVATIONS_COLLECTION = "reservations"
private const val USER_RESERVATIONS_COLLECTION = "user_reservations"

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    var response: DataState by mutableStateOf(DataState.Empty)
        private set

    fun fetchUserReservations() {
        if (response !is DataState.Loading) {
            response = DataState.Loading

            val userEmail = auth.currentUser?.email ?: ""

            db.collection(RESERVATIONS_COLLECTION)
                .document(userEmail)
                .collection(USER_RESERVATIONS_COLLECTION)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val reservations = mutableListOf<Reservation>()
                    if (!querySnapshot.isEmpty) {
                        for (document in querySnapshot) {
                            val reservation = document.toObject(Reservation::class.java)

                            reservation.id = document.id
                            reservations.add(reservation)
                        }
                    }
                    response = DataState.SuccessReservations(reservations)
                }
                .addOnFailureListener { exception ->
                    response = DataState.Failure(exception.message ?: "")
                }
        }
    }

    fun addReservation(
        currentUserEmail: String,
        restaurantId: String,
        restaurantName: String,
        numberOfPeople: Int
    ) {

        val reservationData = hashMapOf(
            "restaurantId" to restaurantId,
            "name" to restaurantName,
            "numberOfPeople" to numberOfPeople
        )

        db.collection(RESERVATIONS_COLLECTION)
            .document(currentUserEmail)
            .collection(USER_RESERVATIONS_COLLECTION)
            .add(reservationData)
            .addOnSuccessListener {
                Log.d(
                    "Firestore",
                    "Reservation added"
                )
            }
            .addOnFailureListener {exception ->
                Log.e(
                    "Firestore",
                    "Failed to add reservation: ${exception.message}"
                )
            }
    }

    fun deleteReservation(reservation: Reservation) {
        val currentUserEmail = auth.currentUser?.email ?: ""

        val collectionRef = db.collection(RESERVATIONS_COLLECTION)
            .document(currentUserEmail)
            .collection(USER_RESERVATIONS_COLLECTION)

        collectionRef
            .document(reservation.id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    collectionRef.document(reservation.id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(
                                "Firestore",
                                "Reservation deleted: ${reservation.id}"
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.e(
                                "Firestore",
                                "Failed to delete reservation: ${exception.message}"
                            )
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "Firestore",
                    "Failed to fetch reservation: ${exception.message}"
                )
            }
    }

}