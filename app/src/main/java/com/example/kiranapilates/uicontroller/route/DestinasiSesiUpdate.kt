package com.example.kiranapilates.uicontroller.route

object DestinasiSesiUpdate : DestinasiNavigasi {
    override val route = "edit_sesi"
    override val titleRes = "Edit Sesi"

    // UBAH JADI INI (Jangan pakai $route)
    const val idWithArgs = "edit_sesi/{id_sesi}"
}