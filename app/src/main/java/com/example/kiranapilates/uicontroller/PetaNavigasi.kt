package com.example.kiranapilates.uicontroller

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kiranapilates.uicontroller.route.DestinasiDaftarPengunjung
import com.example.kiranapilates.uicontroller.route.DestinasiDetailPengunjung
import com.example.kiranapilates.uicontroller.route.DestinasiEditPengunjung
import com.example.kiranapilates.uicontroller.route.DestinasiTambahPengunjung
import com.example.kiranapilates.view.HalamanDaftarPengunjung
import com.example.kiranapilates.view.HalamanDetailPengunjung
import com.example.kiranapilates.view.HalamanEditPengunjung
import com.example.kiranapilates.view.HalamanTambahPengunjung
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@Composable
fun PetaNavigasi() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DestinasiDaftarPengunjung.route
    ) {
        // Halaman 3: Daftar Pengunjung
        composable(route = DestinasiDaftarPengunjung.route) {
            HalamanDaftarPengunjung(
                viewModel = viewModel(factory = PenyediaViewModel.Factory),
                onNavigateToTambah = {

                },
                onNavigateToDetail = { id ->

                }
            )
        }
        composable(route = DestinasiTambahPengunjung.route) {
            HalamanTambahPengunjung(
                viewModel = viewModel(factory = PenyediaViewModel.Factory),
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailPengunjung.idWithArgs,
            arguments = listOf(navArgument("id_pengunjung") { type = NavType.StringType })
        ) {
            HalamanDetailPengunjung(
                viewModel = viewModel(factory = PenyediaViewModel.Factory),
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiEditPengunjung.idWithArgs,
            arguments = listOf(navArgument("id_pengunjung") { type = NavType.StringType })
        ) {
            HalamanEditPengunjung(
                viewModel = viewModel(factory = PenyediaViewModel.Factory),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}