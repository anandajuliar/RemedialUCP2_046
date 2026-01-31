package com.example.ucp2.view.route

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Perpustakaan"
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "entry_buku"
    override val titleRes = "Tambah Buku"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail_buku"
    override val titleRes = "Detail Buku"
    const val idBuku = "itemId"
    val routeWithArgs = "$route/{$idBuku}"
}

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update_buku"
    override val titleRes = "Edit Buku"
    const val idBuku = "itemId"
    val routeWithArgs = "$route/{$idBuku}"
}

object DestinasiEntryTipe : DestinasiNavigasi {
    override val route = "entry_tipe"
    override val titleRes = "Tambah Kategori Buku"
}

object DestinasiListTipe : DestinasiNavigasi {
    override val route = "list_tipe"
    override val titleRes = "Daftar Kategori"
}