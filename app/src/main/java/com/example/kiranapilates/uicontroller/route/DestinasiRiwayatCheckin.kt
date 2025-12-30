package com.example.kiranapilates.uicontroller.route

import com.example.kiranapilates.R

object DestinasiRiwayatCheckin : DestinasiNavigasi {
    override val route = "riwayat_checkin"
    override val titleRes = R.string.riwayat_checkin
    const val sesiIdArg = "sesiId"
    const val tanggalArg = "tanggal"
    val routeWithArgs = "$route/{$sesiIdArg}/{$tanggalArg}"
}