package com.example.ucp2.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.room.Kategori
import com.example.ucp2.view.uicontroller.HotelAppTopAppBar
import com.example.ucp2.viewmodel.EntryTipeViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanListTipe(
    navigateBack: () -> Unit,
    onNavigateToEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryTipeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val listTipe by viewModel.tipeBukuList.collectAsState()
    val scope = rememberCoroutineScope()

    // State untuk Dialog Hapus
    var showDialog by remember { mutableStateOf(false) }
    var selectedTipe by remember { mutableStateOf<Kategori?>(null) }

    Scaffold(
        topBar = {
            HotelAppTopAppBar(
                title = "Daftar Kategori Buku",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToEntry
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Kategori")
            }
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            if (listTipe.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada Tipe Buku")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listTipe) { tipe ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = tipe.namaKategori, style = MaterialTheme.typography.titleLarge)
                                    Text(text = tipe.deskripsi, style = MaterialTheme.typography.bodyMedium)
                                }
                                IconButton(onClick = {
                                    selectedTipe = tipe
                                    showDialog = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Hapus",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog && selectedTipe != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Hapus ${selectedTipe!!.namaKategori}?") },
                text = { Text("Pilih aksi penghapusan untuk tipe ini:") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                viewModel.deleteTipe(selectedTipe!!, hapusBukuJuga = true)
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Hapus Tipe & Buku", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                viewModel.deleteTipe(selectedTipe!!, hapusBukuJuga = false)
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Hapus Tipe Saja")
                    }
                }
            )
        }
    }
}