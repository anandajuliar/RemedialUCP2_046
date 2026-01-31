package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repositori.RepositoriPerpustakaan
import com.example.ucp2.room.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EntryTipeViewModel(private val repositoriPerpustakaan: RepositoriPerpustakaan) : ViewModel() {
    var uiStateTipe by mutableStateOf(KategoriUiState())
        private set

    fun updateUiState(tipeUiState: KategoriUiState) {
        uiStateTipe = tipeUiState
    }

    suspend fun saveTipe() {
        if (validasiInput()) {
            repositoriPerpustakaan.insertTipe(uiStateTipe.toKategori())
        }
    }

    private fun validasiInput(): Boolean {
        return uiStateTipe.namaKategori.isNotBlank() && uiStateTipe.deskripsi.isNotBlank()
    }

    suspend fun deleteTipe(tipeBuku: Kategori, hapusBuku: Boolean) {
        repositoriPerpustakaan.deleteTipe(tipeBuku, hapusBuku)
    }

    val tipeBukuList = repositoriPerpustakaan.getAllTipe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )
}

data class KategoriUiState(
    val idKategori: Int = 0,
    val namaKategori: String = "",
    val deskripsi: String = ""
)

fun KategoriUiState.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsi = deskripsi
)