package com.example.carsinfo

import androidx.lifecycle.*
import com.example.carsinfo.data.Cars
import com.example.carsinfo.data.CarsDao
import kotlinx.coroutines.launch

class CarsViewModel(private val carsDao: CarsDao) : ViewModel() {
    val allCarsInfo:LiveData<List<Cars>> = carsDao.getAll().asLiveData()

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

    fun delete(cars: Cars){
        viewModelScope.launch {
            carsDao.delete(cars)
        }
    }
    fun retrieveCarsInfo(id:Int):LiveData<Cars>{
       return carsDao.getCar(id).asLiveData()
    }

    private fun updata(cars: Cars){
        viewModelScope.launch {
            carsDao.updata(cars)
        }

    }
    private fun getUpdateCarEntry(
        id: Int,name: String,type: String,price: String
    ):Cars{
       return Cars(
           id = id,
           carName = name,
           carType = type,
           carPrice = price.toDouble()
       )
    }
    fun updateCarsInfo(
        id: Int,name: String,type: String,price: String
    ){
        val updateCars = getUpdateCarEntry(id, name, type, price)
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