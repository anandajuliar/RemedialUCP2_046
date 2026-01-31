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
import com.example.ucp2.room.TipeKamar
import com.example.ucp2.view.route.DestinasiEntry
import com.example.ucp2.view.uicontroller.HotelAppTopAppBar
import com.example.ucp2.viewmodel.DetailKamarUiState
import com.example.ucp2.viewmodel.EntryKamarViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryKamarViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val listTipeKamar by viewModel.listTipeKamar.collectAsState()

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
            uiStateKamar = viewModel.uiStateKamar,
            onKamarValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveKamar()
                    navigateBack()
                }
            },
            listTipeKamar = listTipeKamar,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    uiStateKamar: DetailKamarUiState,
    onKamarValueChange: (DetailKamarUiState) -> Unit,
    onSaveClick: () -> Unit,
    listTipeKamar: List<TipeKamar>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputKamar(
            detailKamar = uiStateKamar,
            onValueChange = onKamarValueChange,
            listTipeKamar = listTipeKamar,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateKamar.nomorKamar.isNotBlank() && uiStateKamar.idTipe != 0,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Data Kamar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputKamar(
    detailKamar: DetailKamarUiState,
    onValueChange: (DetailKamarUiState) -> Unit,
    listTipeKamar: List<TipeKamar>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = detailKamar.nomorKamar,
            onValueChange = { onValueChange(detailKamar.copy(nomorKamar = it)) },
            label = { Text("Nomor Kamar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = detailKamar.kapasitas,
            onValueChange = { onValueChange(detailKamar.copy(kapasitas = it)) },
            label = { Text("Kapasitas (Orang)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        OutlinedTextField(
            value = detailKamar.statusKamar,
            onValueChange = { onValueChange(detailKamar.copy(statusKamar = it)) },
            label = { Text("Status Kamar") },
            placeholder = { Text("Contoh: Tersedia, Dibersihkan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Pilih Tipe Kamar",
            style = MaterialTheme.typography.titleMedium
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = detailKamar.namaTipeSelected.ifEmpty { "Pilih Tipe" },
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
                if (listTipeKamar.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Belum ada Tipe Kamar. Tambah dulu!") },
                        onClick = { expanded = false }
                    )
                } else {
                    listTipeKamar.forEach { tipe ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(text = tipe.namaTipe, style = MaterialTheme.typography.bodyLarge)
                                    Text(text = tipe.deskripsi, style = MaterialTheme.typography.labelSmall)
                                }
                            },
                            onClick = {
                                onValueChange(detailKamar.copy(
                                    idTipe = tipe.idTipe,
                                    namaTipeSelected = tipe.namaTipe
                                ))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        if (detailKamar.idTipe == 0) {
            Text(
                text = "*Wajib memilih tipe kamar",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}