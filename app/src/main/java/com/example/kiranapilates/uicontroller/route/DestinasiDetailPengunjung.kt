package com.example.kiranapilates.uicontroller.route

object DestinasiDetailPengunjung : DestinasiNavigasi {
    override val route = "detail_pengunjung"
    override val titleRes = "Detail Pengunjung"
    // Alamat lengkap dengan parameter ID
    const val idWithArgs = "$route/{id_pengunjung}"
}