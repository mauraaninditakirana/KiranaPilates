package com.example.kiranapilates.modeldata
import kotlinx.serialization.Serializable

@Serializable
data class Sesi(
    val id_sesi: String,
    val nama_sesi: String,
    val jam_operasional: String,
    val nama_instruktur: String
)

@Serializable
data class SesiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Sesi>
)