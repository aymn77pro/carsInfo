package com.example.carsinfo.data

import android.content.ClipData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "cars")
data class Cars(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "name")
    var carName:String,
    @ColumnInfo(name = "type")
    var carType:String,
    @ColumnInfo(name = "price")
    var carPrice:Double )

fun Cars.getFormattedPrice(): String =
    java.text.NumberFormat.getCurrencyInstance().format(carPrice)