package com.example.ucp2.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "buku",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["idKategori"],
            childColumns = ["idKategori"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["idKategori"])]
)
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val idBuku: Int = 0,
    val nomorBuku: String,
    val statusBuku: String,
    val penulis: String,
    val idKategori: Int?
)