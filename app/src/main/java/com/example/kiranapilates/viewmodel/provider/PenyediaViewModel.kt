package com.example.kiranapilates.viewmodel.provider


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kiranapilates.repositori.KiranaPilatesApp
import com.example.kiranapilates.viewmodel.CheckinViewModel
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel
import com.example.kiranapilates.viewmodel.DetailPengunjungViewModel
import com.example.kiranapilates.viewmodel.EditPengunjungViewModel
import com.example.kiranapilates.viewmodel.EditSesiViewModel
import com.example.kiranapilates.viewmodel.SesiViewModel
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
        initializer {
            DetailPengunjungViewModel(
                this.createSavedStateHandle(), kiranaPilatesApp().container.pengunjungRepository) }
        initializer { EditPengunjungViewModel(this.createSavedStateHandle(), kiranaPilatesApp().container.pengunjungRepository)
        }
        initializer {
            SesiViewModel(kiranaPilatesApp().container.sesiRepository)
        }
        initializer {
            EditSesiViewModel(
                this.createSavedStateHandle(),
                kiranaPilatesApp().container.sesiRepository
            )
        }
        initializer {
            CheckinViewModel()
        }
    }
}


fun CreationExtras.kiranaPilatesApp(): KiranaPilatesApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KiranaPilatesApp)