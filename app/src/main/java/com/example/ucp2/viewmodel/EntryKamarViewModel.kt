package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repositori.RepositoriHotel
import com.example.ucp2.room.Kamar
import com.example.ucp2.room.TipeKamar
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class EntryKamarViewModel(private val repositoriHotel: RepositoriHotel) : ViewModel() {

    var uiStateKamar by mutableStateOf(DetailKamarUiState())
        private set

    val listTipeKamar: StateFlow<List<TipeKamar>> = repositoriHotel.getAllTipe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun updateUiState(detailKamarUiState: DetailKamarUiState) {
        uiStateKamar = detailKamarUiState
    }

    suspend fun saveKamar() {
        if (validasiInput()) {
            repositoriHotel.insertKamar(uiStateKamar.toKamar())
        }
    }

    private fun validasiInput(uiState: DetailKamarUiState = uiStateKamar): Boolean {
        return with(uiState) {
            nomorKamar.isNotBlank() && statusKamar.isNotBlank() && idTipe != 0
        }
    }
}

data class DetailKamarUiState(
    val idKamar: Int = 0,
    val nomorKamar: String = "",
    val statusKamar: String = "",
    val kapasitas: String = "",
    val idTipe: Int = 0,
    val namaTipeSelected: String = ""
)

fun DetailKamarUiState.toKamar(): Kamar = Kamar(
    idKamar = idKamar,
    nomorKamar = nomorKamar,
    statusKamar = statusKamar,
    kapasitas = kapasitas.toIntOrNull() ?: 0,
    idTipe = if (idTipe == 0) null else idTipe
)