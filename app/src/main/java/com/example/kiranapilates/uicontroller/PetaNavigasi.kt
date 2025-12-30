package com.example.kiranapilates.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kiranapilates.uicontroller.route.*
import com.example.kiranapilates.view.*

@Composable
fun PetaNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    token: String = ""
) {
    var tokenState by remember { mutableStateOf(token) }

    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = modifier
    ) {
        // Halaman 1: Login
        composable(route = DestinasiLogin.route) {
            HalamanLogin(
                onLoginSuccess = { tokenBaru ->
                    tokenState = tokenBaru
                    navController.navigate(DestinasiDashboard.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman 2: Dashboard
        composable(route = DestinasiDashboard.route) {
            HalamanDashboard(
                onNavigateToRegistrasi = { navController.navigate(DestinasiDaftarPengunjung.route) },
                onNavigateToSesi = { navController.navigate(DestinasiSesi.route) },
                onNavigateToCheckin = { navController.navigate(DestinasiCheckin.route) },
                onLogout = {
                    tokenState = ""
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiDashboard.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman 3: Daftar Pengunjung
        composable(route = DestinasiDaftarPengunjung.route) {
            HalamanDaftarPengunjung(
                onTambahPengunjung = { navController.navigate(DestinasiTambahPengunjung.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPengunjung.route}/$id")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Halaman 4: Tambah Pengunjung
        composable(route = DestinasiTambahPengunjung.route) {
            HalamanTambahPengunjung(
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }

        // Halaman 5: Detail Pengunjung
        composable(
            route = DestinasiDetailPengunjung.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPengunjung.PENGUNJUNG_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            HalamanDetailPengunjung(
                onEditClick = { id ->
                    navController.navigate("${DestinasiEditPengunjung.route}/$id")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Halaman 6: Edit Pengunjung
        composable(
            route = DestinasiEditPengunjung.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPengunjung.PENGUNJUNG_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiEditPengunjung.PENGUNJUNG_ID_ARG)
            HalamanEditPengunjung(
                token = tokenState,
                onBack = {
                    navController.navigate(DestinasiDaftarPengunjung.route) {
                        // Hapus semua history sampai ke Dashboard agar bersih
                        popUpTo(DestinasiDashboard.route) { inclusive = false }
                    }
                    navController.navigate("${DestinasiDetailPengunjung.route}/$id") {
                        popUpTo(DestinasiDaftarPengunjung.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        // Halaman 7: Daftar Sesi
        composable(route = DestinasiSesi.route) {
            HalamanSesi(
                onEditSesi = { id ->
                    navController.navigate("${DestinasiSesiUpdate.route}/$id")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Halaman 8: Update Sesi
        composable(
            route = DestinasiSesiUpdate.routeWithArgs,
            arguments = listOf(navArgument(DestinasiSesiUpdate.SESI_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            HalamanSesiUpdate(
                token = tokenState,
                onBack = {
                    navController.navigate(DestinasiSesi.route) {
                        popUpTo(DestinasiSesi.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Halaman 9: Menu Checkin
        composable(route = DestinasiCheckin.route) {
            HalamanCheckinMenu(
                onSesiClick = { idSesi, tanggal ->
                    navController.navigate("${DestinasiRiwayatCheckin.route}/$idSesi/$tanggal")
                },
                onAddCheckin = { navController.navigate(DestinasiCheckinForm.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Halaman 10: Form Checkin
        composable(route = DestinasiCheckinForm.route) {
            HalamanCheckinForm(
                onBack = { navController.popBackStack() },
                onEditPengunjung = { id ->
                    navController.navigate("${DestinasiEditPengunjung.route}/$id")
                }
            )
        }

        // Halaman 11: Riwayat Checkin
        composable(
            route = DestinasiRiwayatCheckin.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiRiwayatCheckin.sesiIdArg) { type = NavType.IntType },
                navArgument(DestinasiRiwayatCheckin.tanggalArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sesiId = backStackEntry.arguments?.getInt(DestinasiRiwayatCheckin.sesiIdArg) ?: 0
            val tanggal = backStackEntry.arguments?.getString(DestinasiRiwayatCheckin.tanggalArg) ?: ""

            HalamanRiwayatCheckin(
                sesiId = sesiId,
                tanggal = tanggal,
                onBack = { navController.navigateUp() }
            )
        }
    }
}