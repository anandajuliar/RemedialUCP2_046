package com.example.ucp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repositori.RepositoriPerpustakaan
import com.example.ucp2.room.BukuDenganKategori
import kotlinx.coroutines.flow.*

class HomeViewModel(private val repositoriPerpustakaan: RepositoriPerpustakaan) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = repositoriPerpustakaan.getAllBuku()
        .filterNotNull()
        .map { HomeUiState(listBuku = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
}

data class HomeUiState(
    val listBuku: List<BukuDenganKategori> = listOf()
)