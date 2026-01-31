package com.example.ucp2

import android.app.Application
import com.example.ucp2.repositori.AppContainer
import com.example.ucp2.repositori.ContainerDataApp

class AplikasiPerpustakaan : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}