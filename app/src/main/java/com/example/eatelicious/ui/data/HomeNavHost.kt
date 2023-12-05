package com.example.eatelicious.ui.data

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eatelicious.activity.AuthActivity
import com.example.eatelicious.auth.AuthViewModel
import com.example.eatelicious.ui.AppScreen
import com.example.eatelicious.ui.data.widgets.TopBar
import com.example.eatelicious.ui.data.widgets.Drawer
import com.example.eatelicious.ui.data.screens.AddReservationScreen
import com.example.eatelicious.ui.data.screens.HomeScreen
import com.example.eatelicious.ui.data.screens.ReservationsScreen
import com.example.eatelicious.ui.data.screens.RestaurantDetailsScreen
import com.example.eatelicious.utils.startNewActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHost() {
    val context = LocalContext.current

    val title = remember { mutableStateOf(AppScreen.Home.title) }
    val navController = rememberNavController()

    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route?.split("/")?.first()) {
            AppScreen.Home.route ->
                title.value = AppScreen.Home.title
            AppScreen.Reservations.route ->
                title.value = AppScreen.Reservations.title
            AppScreen.ReserveTable.route ->
                title.value = AppScreen.ReserveTable.title
            AppScreen.RestaurantDetails.route ->
                title.value = AppScreen.RestaurantDetails.title
        }
    }

    val viewModel: AuthViewModel = hiltViewModel()

    val currentUserEmail = viewModel.currentUser.value?.email ?: ""

    Surface(color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    currentUserEmail = currentUserEmail,
                    onDestinationClicked = { route ->
                        scope.launch { drawerState.close() }
                        if (route == AppScreen.Logout.route) {
                            viewModel.logout()
                            context.startNewActivity(AuthActivity::class.java)
                        } else {
                            navController.navigate(route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        ) {
            TopBar(
                title = title.value,
                onButtonClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                content = {
                    NavigationGraph(
                        navController = navController,
                        currentUserEmail = currentUserEmail
                    )
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, currentUserEmail: String) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                hiltViewModel()
            ) { restaurantName: String ->
                navController.navigate(
                    "${AppScreen.RestaurantDetails.route}/$restaurantName"
                )
            }
        }
        composable(
            route = "${AppScreen.RestaurantDetails.route}/{restaurantId}",
            arguments = listOf(
                navArgument("restaurantId") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            restaurantId?.let { restaurantId ->
                RestaurantDetailsScreen(
                    hiltViewModel(),
                    restaurantId
                ) { id: String, name: String ->
                    navController.navigate(
                        "${AppScreen.ReserveTable.route}/$id/$name"
                    )
                }
            }
        }
        composable(
            route = "${AppScreen.ReserveTable.route}/{restaurantId}/{restaurantName}",
            arguments = listOf(
                navArgument("restaurantId") { defaultValue = "" },
                navArgument("restaurantName") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            val restaurantName = backStackEntry.arguments?.getString("restaurantName")

            restaurantId?.let {
                AddReservationScreen(
                    hiltViewModel(), currentUserEmail, restaurantId, restaurantName!!
                ) {
                    navController.navigate(
                        AppScreen.Reservations.route
                    )
                }
            }
        }
        composable(AppScreen.Reservations.route) {
            ReservationsScreen(hiltViewModel())
        }
    }
}
