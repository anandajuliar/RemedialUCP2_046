package com.example.ucp2.repositori

import com.example.ucp2.room.PerpustakaanDao
import com.example.ucp2.room.Buku
import com.example.ucp2.room.BukuDenganKategori
import com.example.ucp2.room.Kategori
import kotlinx.coroutines.flow.Flow

interface RepositoriPerpustakaan {
    fun getAllTipe(): Flow<List<Kategori>>
    fun getAllBuku(): Flow<List<BukuDenganKategori>>
    suspend fun updateTipe(kategori: Kategori)
    suspend fun insertTipe(tipeBuku: Kategori)
    suspend fun insertBuku(buku: Buku)
    suspend fun deleteTipe(tipeBuku: Kategori, hapusBuku: Boolean)
}

class OfflineRepositoriPerpustakaan(private val perpustakaanDao: PerpustakaanDao) : RepositoriPerpustakaan {
    override fun getAllTipe(): Flow<List<Kategori>> = perpustakaanDao.getAllTipe()
    override fun getAllBuku(): Flow<List<BukuDenganKategori>> = perpustakaanDao.getAllBukuWithTipe()
    override suspend fun insertTipe(tipeBuku: Kategori) = perpustakaanDao.insertTipe(tipeBuku)
    override suspend fun insertBuku(buku: Buku) = perpustakaanDao.insertBuku(buku)
    override suspend fun updateTipe(kategori: Kategori) = perpustakaanDao.updateTipe(kategori)
    override suspend fun deleteTipe(tipeBuku: Kategori, hapusBuku: Boolean) {
        if (hapusBuku) {
            perpustakaanDao.deleteBukuByTipe(tipeBuku.idKategori)
        }
        perpustakaanDao.deleteTipe(tipeBuku)
    }


}