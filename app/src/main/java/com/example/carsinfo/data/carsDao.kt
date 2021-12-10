package com.example.carsinfo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car: Cars)

    @Update
    suspend fun updata(car: Cars)

    @Delete
    suspend fun delete(car: Cars)

    @Query("SELECT * FROM Cars  ORDER BY name ASC")
    fun getAll(): Flow<List<Cars>>

    @Query("SELECT * FROM Cars WHERE id = :id")
    fun getCar(id: Int): Flow<Cars>
}