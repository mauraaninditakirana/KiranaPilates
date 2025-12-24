package com.example.kiranapilates.viewmodel.provider


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kiranapilates.repositori.KiranaPilatesApp
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel
import com.example.kiranapilates.viewmodel.TambahPengunjungViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            // Memanggil repository dari container yang ada di KiranaPilatesApp
            DaftarPengunjungViewModel(kiranaPilatesApp().container.pengunjungRepository)
        }
        initializer {
            TambahPengunjungViewModel(kiranaPilatesApp().container.pengunjungRepository)
        }
    }
}

fun CreationExtras.kiranaPilatesApp(): KiranaPilatesApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KiranaPilatesApp)