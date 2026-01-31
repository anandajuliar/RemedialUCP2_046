package com.example.ucp2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TipeKamar::class, Kamar::class], version = 1, exportSchema = false)
abstract class HotelDatabase : RoomDatabase() {
    abstract fun hotelDao(): HotelDao

    companion object {
        @Volatile
        private var Instance: HotelDatabase? = null

        fun getDatabase(context: Context): HotelDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HotelDatabase::class.java, "hotel_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}