package com.example.carsinfo

import android.app.Application
import com.example.carsinfo.data.CarsRoomDatabase

class CarsInfoApp : Application() {
    val database : CarsRoomDatabase by lazy { CarsRoomDatabase.getDatabase(this) }
}