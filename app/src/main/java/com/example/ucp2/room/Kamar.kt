package com.example.ucp2.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "kamar",
    foreignKeys = [
        ForeignKey(
            entity = TipeKamar::class,
            parentColumns = ["idTipe"],
            childColumns = ["idTipe"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["idTipe"])]
)
data class Kamar(
    @PrimaryKey(autoGenerate = true)
    val idKamar: Int = 0,
    val nomorKamar: String,
    val statusKamar: String,
    val kapasitas: Int,
    val idTipe: Int?
)