package com.example.ucp2.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.room.BukuDenganKategori
import com.example.ucp2.view.route.DestinasiHome
import com.example.ucp2.view.uicontroller.PerpustakaanAppTopAppBar
import com.example.ucp2.viewmodel.HomeViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateToItemEntry: () -> Unit,
    navigateToTipeEntry: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PerpustakaanAppTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExtendedFloatingActionButton(
                    onClick = navigateToTipeEntry,
                    text = { Text("Tambah Kategori") },
                    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )

                FloatingActionButton(
                    onClick = navigateToItemEntry,
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Buku")
                }
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = homeUiState.listBuku,
            retryAction = {},
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: List<BukuDenganKategori>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit
) {
    if (homeUiState.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Tidak ada data buku")
        }
    } else {
        BukuLayout(
            listBuku = homeUiState,
            modifier = modifier,
            onItemClick = { onDetailClick(it.buku.idBuku) }
        )
    }
}

@Composable
fun BukuLayout(
    listBuku: List<BukuDenganKategori>,
    modifier: Modifier = Modifier,
    onItemClick: (BukuDenganKategori) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listBuku) { buku ->
            BukuCard(
                buku = buku,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(buku) }
            )
        }
    }
}

@Composable
fun BukuCard(
    buku: BukuDenganKategori,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buku.buku.nomorBuku,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = buku.namaKategori ?: "Tanpa Tipe",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = "Status: ${buku.buku.statusBuku}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Penulis: ${buku.buku.penulis}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}