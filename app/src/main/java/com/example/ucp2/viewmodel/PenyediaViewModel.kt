package com.example.ucp2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.AplikasiHotel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            EntryKamarViewModel(
                aplikasiHotel().container.repositoriHotel
            )
        }
        initializer {
            HomeViewModel(aplikasiHotel().container.repositoriHotel)
        }
        initializer {
            EntryTipeViewModel(aplikasiHotel().container.repositoriHotel)
        }
    }
}

fun CreationExtras.aplikasiHotel(): AplikasiHotel =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiHotel)