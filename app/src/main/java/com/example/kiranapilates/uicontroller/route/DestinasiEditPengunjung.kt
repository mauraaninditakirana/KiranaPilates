package com.example.kiranapilates.uicontroller.route


object DestinasiEditPengunjung : DestinasiNavigasi {
    override val route = "edit_pengunjung"
    override val titleRes = "Edit Pengunjung"
    // Alamat lengkap dengan parameter ID yang akan diedit
    const val idWithArgs = "edit_pengunjung/{id_pengunjung}"
}