package com.example.ucp2.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.room.Kategori
import com.example.ucp2.view.route.DestinasiEntry
import com.example.ucp2.view.uicontroller.HotelAppTopAppBar
import com.example.ucp2.viewmodel.DetailBukuUiState
import com.example.ucp2.viewmodel.EntryBukuViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val listKategori by viewModel.listKategori.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HotelAppTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            uiStateBuku = viewModel.uiStateBuku,
            onBukuValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveBuku()
                    navigateBack()
                }
            },
            listKategori = listKategori,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    uiStateBuku: DetailBukuUiState,
    onBukuValueChange: (DetailBukuUiState) -> Unit,
    onSaveClick: () -> Unit,
    listKategori: List<Kategori>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputBuku(
            detailBuku = uiStateBuku,
            onValueChange = onBukuValueChange,
            listKategori = listKategori,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateBuku.nomorBuku.isNotBlank() && uiStateBuku.idKategori != 0,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Data Buku")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputBuku(
    detailBuku: DetailBukuUiState,
    onValueChange: (DetailBukuUiState) -> Unit,
    listKategori: List<Kategori>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = detailBuku.nomorBuku,
            onValueChange = { onValueChange(detailBuku.copy(nomorBuku = it)) },
            label = { Text("Nomor Buku") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = detailBuku.penulis,
            onValueChange = { onValueChange(detailBuku.copy(penulis = it)) },
            label = { Text("Penulis") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = detailBuku.statusBuku,
            onValueChange = { onValueChange(detailBuku.copy(statusBuku = it)) },
            label = { Text("Status Buku") },
            placeholder = { Text("Contoh: Tersedia, Dipinjam") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Pilih Tipe Buku",
            style = MaterialTheme.typography.titleMedium
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = detailBuku.namaKategoriSelected.ifEmpty { "Pilih Tipe" },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (listKategori.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Belum ada Tipe Buku. Tambah dulu!") },
                        onClick = { expanded = false }
                    )
                } else {
                    listKategori.forEach { tipe ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(text = tipe.namaKategori, style = MaterialTheme.typography.bodyLarge)
                                    Text(text = tipe.deskripsi, style = MaterialTheme.typography.labelSmall)
                                }
                            },
                            onClick = {
                                onValueChange(detailBuku.copy(
                                    idKategori = tipe.idKategori,
                                    namaKategoriSelected = tipe.namaKategori
                                ))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        if (detailBuku.idKategori == 0) {
            Text(
                text = "*Wajib memilih tipe buku",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}