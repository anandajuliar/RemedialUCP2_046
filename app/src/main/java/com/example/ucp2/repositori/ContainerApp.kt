package com.example.ucp2.repositori

import android.content.Context
import com.example.ucp2.room.PerpustakaanDatabase

interface AppContainer {
    val repositoriPerpustakaan: RepositoriPerpustakaan
}

class ContainerDataApp(private val context: Context) : AppContainer {
    override val repositoriPerpustakaan: RepositoriPerpustakaan by lazy {
        OfflineRepositoriPerpustakaan(PerpustakaanDatabase.getDatabase(context).perpustakaanDao())
    }
}