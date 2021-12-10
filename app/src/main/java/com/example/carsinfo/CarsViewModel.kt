package com.example.carsinfo

import androidx.lifecycle.*
import com.example.carsinfo.data.Cars
import com.example.carsinfo.data.CarsDao
import kotlinx.coroutines.launch

class CarsViewModel(private val carsDao: CarsDao) : ViewModel() {
    // call the list of items from database
    val allCarsInfo: LiveData<List<Cars>> = carsDao.getAll().asLiveData()

    // the logic help to make sure user put all info
    fun isEntryValid(carName: String, carType: String, carPrice: String): Boolean {
        if (carName.isBlank() || carType.isBlank() || carPrice.isBlank()) {
            return false
        }
        return true
    }
    // call insert from your D.A.O
    private fun insertCars(cars: Cars) {
        viewModelScope.launch {
            carsDao.insert(cars)
        }
    }
// to call it in the fragment
    fun addNewCar(name: String, type: String, price: String) {
        val newCar = getNewCarInfoEntry(name, type, price)
        insertCars(newCar)
    }

//returns the inputs as an object of the list
    private fun getNewCarInfoEntry(name: String, type: String, price: String): Cars {
        return Cars(
            carName = name,
            carType = type,
            carPrice = price.toDouble()
        )
    }
// here call delete from D.a.o and can call it in fragment
    fun delete(cars: Cars) {
        viewModelScope.launch {
            carsDao.delete(cars)
        }
    }
    // for edit here user can get id
    fun retrieveCarsInfo(id: Int): LiveData<Cars> {
        return carsDao.getCar(id).asLiveData()
    }
// call up updata from list D.a.o
    private fun updata(cars: Cars) {
        viewModelScope.launch {
            carsDao.updata(cars)
        }

    }
// to change item and update new item
    private fun getUpdateCarEntry(
        id: Int, name: String, type: String, price: String
    ): Cars {
        return Cars(
            id = id,
            carName = name,
            carType = type,
            carPrice = price.toDouble()
        )
    }
// call in fragment
    fun updateCarsInfo(
        id: Int, name: String, type: String, price: String
    ) {
        val updateCars = getUpdateCarEntry(id, name, type, price)
        updata(updateCars)
    }

}

// factory if your viewModel have parameter you need it to copy it and change viewModel name
class CarsViewModelFactory(private val carsDao: CarsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(carsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}