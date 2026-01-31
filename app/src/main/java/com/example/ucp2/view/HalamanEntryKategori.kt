package com.example.ucp2.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.view.route.DestinasiEntryTipe
import com.example.ucp2.view.uicontroller.HotelAppTopAppBar
import com.example.ucp2.viewmodel.EntryTipeViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryTipe(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryTipeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HotelAppTopAppBar(
                title = DestinasiEntryTipe.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val uiState = viewModel.uiStateTipe

            OutlinedTextField(
                value = uiState.namaKategori,
                onValueChange = { viewModel.updateUiState(uiState.copy(namaKategori = it)) },
                label = { Text("Nama Ketegori (Ex: Fiksi)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.deskripsi,
                onValueChange = { viewModel.updateUiState(uiState.copy(deskripsi = it)) },
                label = { Text("Deskripsi / Fasilitas") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.saveTipe()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.namaKategori.isNotBlank()
            ) {
                Text("Simpan Tipe Buku")
            }
        }
    }
}