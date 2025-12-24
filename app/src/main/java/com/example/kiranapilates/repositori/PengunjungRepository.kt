package com.example.kiranapilates.repositori


import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.BasicResponse
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.modeldata.PengunjungResponse

interface PengunjungRepository {
    suspend fun getPengunjung(token: String): PengunjungResponse
    suspend fun insertPengunjung(token: String, pengunjung: Pengunjung): BasicResponse
    suspend fun updatePengunjung(token: String, id: String, pengunjung: Pengunjung): BasicResponse
    suspend fun deletePengunjung(token: String, id: String): BasicResponse
}

class NetworkPengunjungRepository(private val kiranaApiService: KiranaApiService) : PengunjungRepository {
    override suspend fun getPengunjung(token: String): PengunjungResponse =
        kiranaApiService.getAllPengunjung(token)

    override suspend fun insertPengunjung(token: String, pengunjung: Pengunjung): BasicResponse =
        kiranaApiService.insertPengunjung(token, pengunjung.nama_lengkap, pengunjung.no_hp, pengunjung.tipe_pengunjung)

    override suspend fun updatePengunjung(token: String, id: String, pengunjung: Pengunjung): BasicResponse =
        kiranaApiService.updatePengunjung(token, id, pengunjung.nama_lengkap, pengunjung.no_hp, pengunjung.tipe_pengunjung)

    override suspend fun deletePengunjung(token: String, id: String): BasicResponse =
        kiranaApiService.deletePengunjung(token, id)
}