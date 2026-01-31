package com.example.ucp2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipe_kamar")
data class TipeKamar(
    @PrimaryKey(autoGenerate = true)
    val idTipe: Int = 0,
    val namaTipe: String,
    val deskripsi: String
)