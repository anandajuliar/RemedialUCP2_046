package com.example.ucp2.view.route

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Hotel"
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "entry_kamar"
    override val titleRes = "Tambah Kamar"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail_kamar"
    override val titleRes = "Detail Kamar"
    const val idKamar = "itemId"
    val routeWithArgs = "$route/{$idKamar}"
}

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update_kamar"
    override val titleRes = "Edit Kamar"
    const val idKamar = "itemId"
    val routeWithArgs = "$route/{$idKamar}"
}

object DestinasiEntryTipe : DestinasiNavigasi {
    override val route = "entry_tipe"
    override val titleRes = "Tambah Tipe Kamar"
}

object DestinasiListTipe : DestinasiNavigasi {
    override val route = "list_tipe"
    override val titleRes = "Daftar Tipe"
}