package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repositori.RepositoriPerpustakaan
import com.example.ucp2.room.Buku
import com.example.ucp2.room.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class EntryBukuViewModel(private val repositoriPerpustakaan: RepositoriPerpustakaan) : ViewModel() {

    var uiStateBuku by mutableStateOf(DetailBukuUiState())
        private set

    val listKategori: StateFlow<List<Kategori>> = repositoriPerpustakaan.getAllTipe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun updateUiState(detailBukuUiState: DetailBukuUiState) {
        uiStateBuku = detailBukuUiState
    }

    suspend fun saveBuku() {
        if (validasiInput()) {
            repositoriPerpustakaan.insertBuku(uiStateBuku.toBuku())
        }
    }

    private fun validasiInput(uiState: DetailBukuUiState = uiStateBuku): Boolean {
        return with(uiState) {
            nomorBuku.isNotBlank() && statusBuku.isNotBlank() && idKategori != 0
        }
    }
}

data class DetailBukuUiState(
    val idBuku: Int = 0,
    val nomorBuku: String = "",
    val statusBuku: String = "",
    val penulis: String = "",
    val idKategori: Int = 0,
    val namaKategoriSelected: String = ""
)

fun DetailBukuUiState.toBuku(): Buku = Buku(
    idBuku = idBuku,
    nomorBuku = nomorBuku,
    statusBuku = statusBuku,
    penulis = penulis,
    idKategori = if (idKategori == 0) null else idKategori
)