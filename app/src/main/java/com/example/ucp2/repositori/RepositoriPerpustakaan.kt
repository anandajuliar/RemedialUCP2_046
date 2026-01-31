package com.example.ucp2.repositori

import com.example.ucp2.room.PerpustakaanDao
import com.example.ucp2.room.Buku
import com.example.ucp2.room.BukuDenganKategori
import com.example.ucp2.room.Kategori
import kotlinx.coroutines.flow.Flow

interface RepositoriHotel {
    fun getAllTipe(): Flow<List<Kategori>>
    fun getAllBuku(): Flow<List<BukuDenganKategori>>

    suspend fun insertTipe(tipeBuku: Kategori)
    suspend fun insertBuku(buku: Buku)
    suspend fun deleteTipe(tipeBuku: Kategori, hapusBukuJuga: Boolean)
}

class OfflineRepositoriHotel(private val hotelDao: PerpustakaanDao) : RepositoriHotel {
    override fun getAllTipe(): Flow<List<Kategori>> = hotelDao.getAllTipe()
    override fun getAllBuku(): Flow<List<BukuDenganKategori>> = hotelDao.getAllBukuWithTipe()

    override suspend fun insertTipe(tipeBuku: Kategori) = hotelDao.insertTipe(tipeBuku)
    override suspend fun insertBuku(buku: Buku) = hotelDao.insertBuku(buku)

    override suspend fun deleteTipe(tipeBuku: Kategori, hapusBukuJuga: Boolean) {
        if (hapusBukuJuga) {
            hotelDao.deleteBukuByTipe(tipeBuku.idKategori)
        }
        hotelDao.deleteTipe(tipeBuku)
    }
}