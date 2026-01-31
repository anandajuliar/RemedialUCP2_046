package com.example.ucp2.repositori

import android.content.Context
import com.example.ucp2.room.HotelDatabase

interface AppContainer {
    val repositoriHotel: RepositoriHotel
}

class ContainerDataApp(private val context: Context) : AppContainer {
    override val repositoriHotel: RepositoriHotel by lazy {
        OfflineRepositoriHotel(HotelDatabase.getDatabase(context).hotelDao())
    }
}