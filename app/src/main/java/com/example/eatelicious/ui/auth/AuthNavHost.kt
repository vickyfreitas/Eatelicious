package com.example.eatelicious.ui.theme.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatelicious.ui.AppScreen
import com.example.eatelicious.ui.theme.auth.screens.LoginScreen
import com.example.eatelicious.ui.theme.auth.screens.SignupScreen

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Auth.Login.route
    ) {
        composable(AppScreen.Auth.Login.route) {
            LoginScreen(
                hiltViewModel()
            ) {
                navController.navigate(AppScreen.Auth.Signup.route) {
                    popUpTo(AppScreen.Auth.Login.route) { inclusive = true }
                }
            }
        }
        composable(AppScreen.Auth.Signup.route) {
            SignupScreen(
                hiltViewModel()
            ) {
                navController.navigate(AppScreen.Auth.Login.route) {
                    popUpTo(AppScreen.Auth.Signup.route) { inclusive = true }
                }
            }
        }
    }
}