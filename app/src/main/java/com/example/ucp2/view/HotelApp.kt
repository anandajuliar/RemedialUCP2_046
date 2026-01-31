package com.example.ucp2.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucp2.view.route.DestinasiEntry
import com.example.ucp2.view.route.DestinasiEntryTipe
import com.example.ucp2.view.route.DestinasiHome
import com.example.ucp2.view.route.DestinasiListTipe

@Composable
fun HotelApp(navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HalamanHome(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToTipeEntry = { navController.navigate(DestinasiListTipe.route) },
                navigateBack = { /* Tidak ada back dari home */ },
                onDetailClick = {
                }
            )
        }

        composable(DestinasiEntry.route) {
            HalamanEntry(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiEntryTipe.route) {
            HalamanEntryTipe(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiListTipe.route) {
            HalamanListTipe(
                navigateBack = { navController.popBackStack() },
                onNavigateToEntry = { navController.navigate(DestinasiEntryTipe.route) }
            )
        }
    }
}