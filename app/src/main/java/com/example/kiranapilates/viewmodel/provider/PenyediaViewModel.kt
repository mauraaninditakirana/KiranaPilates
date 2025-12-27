package com.example.kiranapilates.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kiranapilates.KiranaApplication
import com.example.kiranapilates.viewmodel.*

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // Initializer untuk Halaman 1 (Login)
        initializer {
            LoginViewModel(
                kiranaApp().container.authRepository
            )
        }

        // Initializer untuk Halaman 2 (Dashboard)
        initializer {
            DashboardViewModel(
                kiranaApp().container.authRepository
            )
        }

        // Initializer untuk Halaman 3 (Daftar Pengunjung & Search)
        initializer {
            DaftarPengunjungViewModel(
                kiranaApp().container.kiranaRepository
            )
        }

        // Initializer untuk Halaman 4 (Tambah Pengunjung)
        initializer {
            TambahPengunjungViewModel(
                kiranaApp().container.kiranaRepository
            )
        }

        // Initializer untuk Halaman 5 (Detail Pengunjung & Hapus)
        initializer {
            DetailPengunjungViewModel(
                kiranaApp().container.kiranaRepository
            )
        }

        // Initializer untuk Halaman 6 (Edit Pengunjung & Tambah Paket)
        initializer {
            EditPengunjungViewModel(
                kiranaApp().container.kiranaRepository
            )
        }

        // Initializer untuk Halaman 7 (List 3 Sesi)
        initializer {
            SesiViewModel(
                kiranaApp().container.sesiRepository,
                kiranaApp().container.authRepository
            )
        }

        // Initializer untuk Halaman 8 (Update Sesi - Jam/Instruktur)
        initializer {
            SesiUpdateViewModel(
                kiranaApp().container.sesiRepository
            )
        }

        // Initializer untuk Halaman 9 (Menu Check-in & Filter Tanggal)
        initializer {
            CheckinMenuViewModel(
                kiranaApp().container.checkinRepository
            )
        }

        // Initializer untuk Halaman 10 (Form Check-in, Dropdown, & Validasi Kuota)
        initializer {
            CheckinFormViewModel(
                kiranaApp().container.checkinRepository,
                kiranaApp().container.kiranaRepository,
                kiranaApp().container.sesiRepository
            )
        }

        // Initializer untuk Halaman 11 (Riwayat Per Sesi & Tanggal)
        initializer {
            RiwayatCheckinViewModel(
                kiranaApp().container.checkinRepository
            )
        }
    }
}

/**
 * Fungsi ekstensi untuk mempermudah akses ke instance KiranaApplication.
 */
fun CreationExtras.kiranaApp(): KiranaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KiranaApplication)