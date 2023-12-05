package com.example.eatelicious.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.eatelicious.R

sealed class AppScreen(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {

    object Auth : AppScreen(R.string.app_name, R.drawable.ic_app_logo, "nav_auth") {
        object Login : AppScreen(R.string.login, R.drawable.ic_app_logo, "login")
        object Signup : AppScreen(R.string.signup, R.drawable.ic_app_logo, "signup")
    }

    object Home : AppScreen(
        R.string.home, R.drawable.ic_my_businesses, "home")
    object Reservations : AppScreen(
        R.string.reservation, R.drawable.ic_dashboard, "reservations")

    object ReserveTable : AppScreen(
        R.string.reserve_table, R.drawable.ic_empty, "reserve")

    object RestaurantDetails : AppScreen(
        R.string.restaurant_details, R.drawable.ic_empty, "restaurant")

    object Logout : AppScreen(
        R.string.logout, R.drawable.ic_logout, "logout")
}
