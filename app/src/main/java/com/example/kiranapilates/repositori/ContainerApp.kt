package com.example.kiranapilates.repositori

import android.app.Application
import com.example.kiranapilates.apiservice.KiranaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// 1. Interface Container (Kontrak)
interface ContainerApp {
    val pengunjungRepository: PengunjungRepository
}

// 2. Implementasi Container
class DefaultContainerApp : ContainerApp {

    private val baseUrl = "http://10.0.2.2/kirana_pilates_api/"

    // Logging agar bisa lihat error API di Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .client(klien)
        .build()

    private val retrofitService: KiranaApiService by lazy {
        retrofit.create(KiranaApiService::class.java)
    }

    override val pengunjungRepository: PengunjungRepository by lazy {
        NetworkPengunjungRepository(retrofitService)
    }
}

// 3. Class Application
class KiranaPilatesApp : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Inisialisasi container saat aplikasi jalan
        this.container = DefaultContainerApp()
    }
}