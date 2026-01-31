package com.example.ucp2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.AplikasiPerpustakaan

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            EntryBukuViewModel(
                aplikasiHotel().container.repositoriPerpustakaan
            )
        }
        initializer {
            HomeViewModel(aplikasiHotel().container.repositoriPerpustakaan)
        }
        initializer {
            EntryTipeViewModel(aplikasiHotel().container.repositoriPerpustakaan)
        }
    }
}

fun CreationExtras.aplikasiHotel(): AplikasiPerpustakaan =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiPerpustakaan)