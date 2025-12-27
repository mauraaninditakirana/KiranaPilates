package com.example.kiranapilates

import android.app.Application
import com.example.kiranapilates.repositori.ContainerApp

class KiranaApplication : Application() {
    /**
     * AppContainer instance digunakan oleh kelas lain untuk mendapatkan dependensi.
     */
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp saat aplikasi dimulai
        container = ContainerApp()
    }
}