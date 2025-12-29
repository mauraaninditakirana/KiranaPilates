package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.PengunjungResponse
import com.example.kiranapilates.modeldata.PengunjungUpdateBody

interface PengunjungRepository {
    suspend fun getAllPengunjung(): PengunjungResponse
    suspend fun insertPengunjung(nama: String, hp: String, tipe: String): PengunjungResponse
    suspend fun updatePengunjung(token: String, data: Map<String, String>): PengunjungResponse
    suspend fun deletePengunjung(id: Int): PengunjungResponse
    suspend fun getPengunjungById(id: Int): PengunjungResponse
}

class OfflinePengunjungRepository(private val kiranaApiService: KiranaApiService) : PengunjungRepository {
    override suspend fun getAllPengunjung(): PengunjungResponse =
        kiranaApiService.getAllPengunjung()

    override suspend fun insertPengunjung(nama: String, hp: String, tipe: String): PengunjungResponse =
        kiranaApiService.createPengunjung(nama, hp, tipe)

    override suspend fun updatePengunjung(token: String, data: Map<String, String>): PengunjungResponse =
        kiranaApiService.updatePengunjung(
            token = token,
            id = data["id_pengunjung"] ?: "",
            nama = data["nama_lengkap"] ?: "",
            hp = data["no_hp"] ?: "",
            tipe = data["tipe_pengunjung"] ?: "",
            tambahPaket = data["tambah_paket"] ?: "0"
        )
    override suspend fun deletePengunjung(id: Int): PengunjungResponse =
        kiranaApiService.deletePengunjung(id)

    override suspend fun getPengunjungById(id: Int): PengunjungResponse =
        kiranaApiService.getPengunjungById(id)
}