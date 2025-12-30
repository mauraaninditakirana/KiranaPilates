package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Checkin(
    val id_checkin: Int = 0,
    val id_pengunjung: Int = 0,
    val id_sesi: Int = 0,
    val tanggal_checkin: String = "",
    val tipe_saat_checkin: String = "",
    val nama_lengkap: String = "",
    val nama_sesi: String = "",
    val jam_operasional: String = ""
)
@Serializable
data class RiwayatCheckinResponse(
    val status: String,
    val message: String,
    val data: List<Checkin>? = emptyList()
)

@Serializable
data class CheckinResponse(
    val status: String,
    val message: String,
    val data: List<Checkin>? = null
)