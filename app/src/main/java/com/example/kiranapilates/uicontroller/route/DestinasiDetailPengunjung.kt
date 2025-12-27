package com.example.kiranapilates.uicontroller.route
import com.example.kiranapilates.R

object DestinasiDetailPengunjung : DestinasiNavigasi {
    override val route = "detail_pengunjung"
    const val PENGUNJUNG_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$PENGUNJUNG_ID_ARG}"
    override val titleRes = R.string.detail_pengunjung
}