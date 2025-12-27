package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Checkin(
    val id_checkin: Int,
    val id_pengunjung: Int,
    val id_sesi: Int,
    val tanggal_checkin: String,
    val tipe_saat_checkin: String,
    val nama_lengkap: String = ""
)

@Serializable
data class CheckinResponse(
    val status: String,
    val message: String,
    val data: List<Checkin>? = null
)