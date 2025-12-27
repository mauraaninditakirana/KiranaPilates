package com.example.kiranapilates.uicontroller.route
import com.example.kiranapilates.R

object DestinasiSesiUpdate : DestinasiNavigasi {
    override val route = "sesi_update"
    const val SESI_ID_ARG = "sesiId"
    val routeWithArgs = "$route/{$SESI_ID_ARG}"
    override val titleRes = R.string.edit_sesi
}