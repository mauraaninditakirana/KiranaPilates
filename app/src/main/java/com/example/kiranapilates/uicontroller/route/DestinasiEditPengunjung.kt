package com.example.kiranapilates.uicontroller.route
import com.example.kiranapilates.R

object DestinasiEditPengunjung : DestinasiNavigasi {
    override val route = "edit_pengunjung"
    const val PENGUNJUNG_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$PENGUNJUNG_ID_ARG}"
    override val titleRes = R.string.edit_pengunjung
}