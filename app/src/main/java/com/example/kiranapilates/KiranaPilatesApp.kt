package com.example.kiranapilates

import android.app.Application
import com.example.kiranapilates.repositori.AppContainer
import com.example.kiranapilates.repositori.KiranaContainer

class KiranaPilatesApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KiranaContainer()
    }
}