package com.example.kiranapilates

import android.app.Application
import com.example.kiranapilates.repositori.ContainerApp
import com.example.kiranapilates.repositori.DefaultContainerApp

class KiranaPilatesApp : Application() {
    // Variabel ini yang dipanggil di PenyediaViewModel
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainerApp()
    }
}