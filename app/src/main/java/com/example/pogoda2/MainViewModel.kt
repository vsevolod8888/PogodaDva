package com.example.pogoda2

import android.app.Application
import androidx.lifecycle.*
import com.example.pogoda2.repozitory_and_dataanswer.ForViewModelWeather
import com.example.pogoda2.repozitory_and_dataanswer.Repository
import com.example.pogoda2.repozitory_and_dataanswer.getDatabase
import kotlinx.coroutines.launch
data class Location(val lat: Double, val lon: Double) {
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    //val weather:List<ForViewModelWeather>
    var coordinatess: Location? = null
    fun saveCurrentCoordinates(lat: Double, lon: Double) {
        coordinatess = Location(lat, lon)
    }
    private val weatherReppozi = Repository(getDatabase(application))
    val weatherrr = weatherReppozi.weather
    val errorr = weatherReppozi.errorMessage

    fun refreshDataFromRepository(
        lat: Double,
        lon: Double
    ) {

        viewModelScope.launch {
            weatherReppozi.refreshWeather2(lat.toString(), lon.toString())
        }
    }

    fun refreshDataFromRepository2(cityName: String) {  // Refresh data from network and pass it via LiveData. Use a coroutine launch to get to background thread.

        viewModelScope.launch {
            weatherReppozi.refreshWeatherByCityName(cityName)
        }
    }

    private val _choosendetail = MutableLiveData<ForViewModelWeather>()
    val choosendetail: LiveData<ForViewModelWeather>
        get() = _choosendetail

    fun onClickDetail(choosenItem: ForViewModelWeather) {
        _choosendetail.value = choosenItem

    }
}