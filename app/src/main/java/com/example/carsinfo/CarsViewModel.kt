package com.example.carsinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carsinfo.data.Cars
import com.example.carsinfo.data.CarsDao
import kotlinx.coroutines.launch

class CarsViewModel(private val carsDao: CarsDao) : ViewModel() {


    fun isEntryValid(carName:String, carType:String, carPrice:String):Boolean{
        if (carName.isBlank()||carType.isBlank()||carPrice.isBlank()){
            return false
        }
        return true
    }
    fun addNewCar(name:String,type:String,price:String){
        val newCar = getNewCarEmtry(name, type, price)
        insertCars(newCar)
    }

    private fun insertCars(cars:Cars){
        viewModelScope.launch {
            carsDao.insert(cars)
        }
    }
    private fun getNewCarEmtry(name:String,type:String,price:String):Cars{
        return Cars(
         carName = name,
         carType = type,
         carPrice = price.toDouble()
        )
    }
}

class CarsViewModelFactory(private val carsDao: CarsDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(carsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}