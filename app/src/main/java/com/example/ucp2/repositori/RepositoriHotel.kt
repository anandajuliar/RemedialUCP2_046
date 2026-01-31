package com.example.ucp2.repositori

import com.example.ucp2.room.HotelDao
import com.example.ucp2.room.Kamar
import com.example.ucp2.room.KamarDenganTipe
import com.example.ucp2.room.TipeKamar
import kotlinx.coroutines.flow.Flow

interface RepositoriHotel {
    fun getAllTipe(): Flow<List<TipeKamar>>
    fun getAllKamar(): Flow<List<KamarDenganTipe>>

    suspend fun insertTipe(tipeKamar: TipeKamar)
    suspend fun insertKamar(kamar: Kamar)

    // Logic Khusus Soal: Hapus Tipe dengan Opsi
    suspend fun deleteTipe(tipeKamar: TipeKamar, hapusKamarJuga: Boolean)
}

class OfflineRepositoriHotel(private val hotelDao: HotelDao) : RepositoriHotel {
    override fun getAllTipe(): Flow<List<TipeKamar>> = hotelDao.getAllTipe()
    override fun getAllKamar(): Flow<List<KamarDenganTipe>> = hotelDao.getAllKamarWithTipe()

    override suspend fun insertTipe(tipeKamar: TipeKamar) = hotelDao.insertTipe(tipeKamar)
    override suspend fun insertKamar(kamar: Kamar) = hotelDao.insertKamar(kamar)

    override suspend fun deleteTipe(tipeKamar: TipeKamar, hapusKamarJuga: Boolean) {
        if (hapusKamarJuga) {
            hotelDao.deleteKamarByTipe(tipeKamar.idTipe)
        }
        hotelDao.deleteTipe(tipeKamar)
    }
}