package com.example.ucp2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipe_buku")
data class Kategori(
    @PrimaryKey(autoGenerate = true)
    val idKategori: Int = 0,
    val namaKategori: String,
    val deskripsi: String
)