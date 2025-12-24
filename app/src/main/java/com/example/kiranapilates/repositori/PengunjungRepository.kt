package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.modeldata.BasicResponse
import com.example.kiranapilates.modeldata.PengunjungResponse

interface PengunjungRepository {
    suspend fun getPengunjung(token: String): PengunjungResponse

    suspend fun insertPengunjung(token: String, pengunjung: Pengunjung): BasicResponse
    suspend fun getPengunjungById(token: String, id: String): Pengunjung
}

    class NetworkPengunjungRepository(private val kiranaApiService: KiranaApiService) : PengunjungRepository {
    override suspend fun getPengunjung(token: String): PengunjungResponse =
        kiranaApiService.getAllPengunjung(token)

        override suspend fun insertPengunjung(token: String, pengunjung: Pengunjung): BasicResponse {
            return kiranaApiService.insertPengunjung(
                token,
                pengunjung.nama_lengkap,
                pengunjung.no_hp,
                pengunjung.tipe_pengunjung
            )
        }

        override suspend fun getPengunjungById(token: String, id: String): Pengunjung {
            val response = kiranaApiService.getAllPengunjung(token)
            return response.data.find { it.id_pengunjung == id }
                ?: throw Exception("Pengunjung tidak ditemukan")
        }
}