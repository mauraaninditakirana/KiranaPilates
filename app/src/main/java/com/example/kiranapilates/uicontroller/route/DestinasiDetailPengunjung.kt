package com.example.kiranapilates.uicontroller.route
object DestinasiDetailPengunjung : DestinasiNavigasi {
    override val route = "detail_pengunjung"
    override val titleRes = "Detail Pengunjung"

    // PERBAIKAN: Gunakan tanda kutip dan dollar sign yang benar
    const val idWithArgs = "detail_pengunjung/{id_pengunjung}"
}