package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repositori.RepositoriHotel
import com.example.ucp2.room.TipeKamar
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EntryTipeViewModel(private val repositoriHotel: RepositoriHotel) : ViewModel() {
    var uiStateTipe by mutableStateOf(TipeKamarUiState())
        private set

    fun updateUiState(tipeUiState: TipeKamarUiState) {
        uiStateTipe = tipeUiState
    }

    suspend fun saveTipe() {
        if (validasiInput()) {
            repositoriHotel.insertTipe(uiStateTipe.toTipeKamar())
        }
    }

    private fun validasiInput(): Boolean {
        return uiStateTipe.namaTipe.isNotBlank() && uiStateTipe.deskripsi.isNotBlank()
    }

    suspend fun deleteTipe(tipeKamar: TipeKamar, hapusKamarJuga: Boolean) {
        repositoriHotel.deleteTipe(tipeKamar, hapusKamarJuga)
    }

    val tipeKamarList = repositoriHotel.getAllTipe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )
}

data class TipeKamarUiState(
    val idTipe: Int = 0,
    val namaTipe: String = "",
    val deskripsi: String = ""
)

fun TipeKamarUiState.toTipeKamar(): TipeKamar = TipeKamar(
    idTipe = idTipe,
    namaTipe = namaTipe,
    deskripsi = deskripsi
)