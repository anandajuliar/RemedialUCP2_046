package com.example.ucp2.room

import androidx.room.Embedded

data class BukuDenganKategori(
    @Embedded val buku: Buku,
    val namaKategori: String?
)