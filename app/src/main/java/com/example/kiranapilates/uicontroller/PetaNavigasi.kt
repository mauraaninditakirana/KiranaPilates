package com.example.kiranapilates.uicontroller

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kiranapilates.uicontroller.route.DestinasiDaftarPengunjung
import com.example.kiranapilates.view.HalamanDaftarPengunjung
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
    }
}