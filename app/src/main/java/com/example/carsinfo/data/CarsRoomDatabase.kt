package com.example.carsinfo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cars::class], version = 1, exportSchema = false)
abstract class CarsRoomDatabase : RoomDatabase() {
    abstract fun carsDao():CarsDao

    companion object{
        @Volatile
        private var INSTANCE:CarsRoomDatabase? = null

        fun getDatabase(context: Context):CarsRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarsRoomDatabase::class.java,
                    "cars_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }

}