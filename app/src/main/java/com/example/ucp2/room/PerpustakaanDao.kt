package com.example.ucp2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PerpustakaanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTipe(tipeBuku: Kategori)

    @Update
    suspend fun updateTipe(tipeBuku: Kategori)

    @Delete
    suspend fun deleteTipe(tipeBuku: Kategori)

    @Query("SELECT * from tipe_buku ORDER BY namaKategori ASC")
    fun getAllTipe(): Flow<List<Kategori>>

    @Query("SELECT * from tipe_buku WHERE idKategori = :id")
    fun getTipe(id: Int): Flow<Kategori>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBuku(buku: Buku)

    @Update
    suspend fun updateBuku(buku: Buku)

    @Delete
    suspend fun deleteBuku(buku: Buku)

    @Query("DELETE FROM buku WHERE idKategori = :idKategori")
    suspend fun deleteBukuByTipe(idKategori: Int)

    @Query("SELECT * from buku WHERE idBuku = :id")
    fun getBuku(id: Int): Flow<Buku>

    @Query("""
        SELECT k.*, t.namaKategori 
        FROM buku k 
        LEFT JOIN tipe_buku t ON k.idKategori = t.idKategori 
        ORDER BY k.nomorBuku ASC
    """)
    fun getAllBukuWithTipe(): Flow<List<BukuDenganKategori>>
}