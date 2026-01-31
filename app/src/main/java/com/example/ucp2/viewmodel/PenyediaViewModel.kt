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
                aplikasiPerpustakaan().container.repositoriPerpustakaan
            )
        }
        initializer {
            HomeViewModel(aplikasiPerpustakaan().container.repositoriPerpustakaan)
        }
        initializer {
            EntryTipeViewModel(aplikasiPerpustakaan().container.repositoriPerpustakaan)
        }
    }
}

fun CreationExtras.aplikasiPerpustakaan(): AplikasiPerpustakaan =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiPerpustakaan)