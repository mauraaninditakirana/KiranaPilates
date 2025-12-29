package com.example.kiranapilates.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue          // Import penting untuk state
import androidx.compose.runtime.mutableStateOf  // Import penting untuk state
import androidx.compose.runtime.remember        // Import penting untuk state
import androidx.compose.runtime.setValue        // Import penting untuk state
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
    // Token awal (bisa kosong jika belum login)
    token: String = ""
) {
    // 1. STATE UNTUK MENYIMPAN TOKEN SELAMA APLIKASI BERJALAN
    // Kita inisialisasi dengan parameter token (jika ada)
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
                    // 2. SAAT LOGIN SUKSES, SIMPAN TOKEN KE STATE
                    // Token ini yang akan dipakai halaman lain
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
                    // Reset token saat logout
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
                // 3. GUNAKAN TOKEN STATE YANG SUDAH TERISI SAAT LOGIN
                token = tokenState,
                onBack = {
                    navController.navigate("${DestinasiDetailPengunjung.route}/$id"){
                        popUpTo(DestinasiDaftarPengunjung.route){
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
                // 4. JANGAN PAKAI STRING HARDCODED, PAKAI TOKEN ASLI
                token = tokenState,
                onBack = { navController.popBackStack() }
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
                navArgument(DestinasiRiwayatCheckin.SESI_ID_ARG) { type = NavType.IntType },
                navArgument(DestinasiRiwayatCheckin.TANGGAL_ARG) { type = NavType.StringType }
            )
        ) {
            HalamanRiwayatCheckin(
                onBack = { navController.popBackStack() }
            )
        }
    }
}