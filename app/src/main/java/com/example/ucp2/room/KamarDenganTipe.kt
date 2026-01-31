package com.example.ucp2.room

import androidx.room.Embedded

data class KamarDenganTipe(
    @Embedded val kamar: Kamar,
    val namaTipe: String?
)