package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val kiranaRepository: PengunjungRepository
    val sesiRepository: SesiRepository
    val checkinRepository: CheckinRepository
    val authRepository: AuthRepository // Tambahan untuk Login/Logout

}

class ContainerApp : AppContainer {
    // GANTI IP INI SESUAI IP LAPTOP (WIFI YANG SAMA DENGAN HP)
    private val baseUrl = "http://10.0.2.2/kirana_pilates_api/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: KiranaApiService by lazy {
        retrofit.create(KiranaApiService::class.java)
    }

    // Inisialisasi semua Repositori
    override val kiranaRepository: PengunjungRepository by lazy {
        OfflinePengunjungRepository(retrofitService)
    }

    override val sesiRepository: SesiRepository by lazy {
        OfflineSesiRepository(retrofitService)
    }

    override val checkinRepository: CheckinRepository by lazy {
        OfflineCheckinRepository(retrofitService)
    }

    override val authRepository: AuthRepository by lazy {
        OfflineAuthRepository(retrofitService)
    }
}