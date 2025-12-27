package com.example.kiranapilates.uicontroller.route
import com.example.kiranapilates.R

object DestinasiRiwayatCheckin : DestinasiNavigasi {
    override val route = "riwayat_checkin"
    const val SESI_ID_ARG = "sesiId"
    const val TANGGAL_ARG = "tanggal"
    val routeWithArgs = "$route/{$SESI_ID_ARG}/{$TANGGAL_ARG}"
    override val titleRes = R.string.riwayat_checkin
}