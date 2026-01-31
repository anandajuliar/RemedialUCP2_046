package com.example.ucp2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelDao {
    // --- Bagian Tipe Kamar ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTipe(tipeKamar: TipeKamar)

    @Update
    suspend fun updateTipe(tipeKamar: TipeKamar)

    @Delete
    suspend fun deleteTipe(tipeKamar: TipeKamar)

    @Query("SELECT * from tipe_kamar ORDER BY namaTipe ASC")
    fun getAllTipe(): Flow<List<TipeKamar>>

    @Query("SELECT * from tipe_kamar WHERE idTipe = :id")
    fun getTipe(id: Int): Flow<TipeKamar>

    // --- Bagian Kamar ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKamar(kamar: Kamar)

    @Update
    suspend fun updateKamar(kamar: Kamar)

    @Delete
    suspend fun deleteKamar(kamar: Kamar)

    @Query("DELETE FROM kamar WHERE idTipe = :idTipe")
    suspend fun deleteKamarByTipe(idTipe: Int)

    @Query("SELECT * from kamar WHERE idKamar = :id")
    fun getKamar(id: Int): Flow<Kamar>

    @Query("""
        SELECT k.*, t.namaTipe 
        FROM kamar k 
        LEFT JOIN tipe_kamar t ON k.idTipe = t.idTipe 
        ORDER BY k.nomorKamar ASC
    """)
    fun getAllKamarWithTipe(): Flow<List<KamarDenganTipe>>
}