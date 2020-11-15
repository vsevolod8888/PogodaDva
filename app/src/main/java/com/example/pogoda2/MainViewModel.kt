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

    var lat = MutableLiveData<Location>()

    private val weatherReppozi = Repository(getDatabase(application))
    val weatherrr = weatherReppozi.weather
    val errorr = weatherReppozi.errorMessage


//    init {
//
//        refreshDataFromRepository()              //этот код извлекает список воспроизведения видео из репозитория, а не напрямую из сети
//
//    }


    fun refreshDataFromRepository(
        lat: Double,
        lon: Double
    ) {    // Refresh data from network and pass it via LiveData. Use a coroutine launch to get to background thread.
        // instanse = MainActivity()

        viewModelScope.launch {
            //  weatherReppozi.refreshWeather()
            weatherReppozi.refreshWeather2(lat.toString(), lon.toString())
        }
    }

    fun refreshDataFromRepository2(cityName: String) {  // Refresh data from network and pass it via LiveData. Use a coroutine launch to get to background thread.

        viewModelScope.launch {
            //  weatherReppozi.refreshWeather()
            weatherReppozi.refreshWeatherByCityName(cityName)
        }
    }

    private val _choosendetail = MutableLiveData<ForViewModelWeather>()
    val choosendetail: LiveData<ForViewModelWeather>
        get() = _choosendetail

//    private var _isClicked = MutableLiveData<Boolean>()
//    val isClicked: LiveData<Boolean>
//        get() = _isClicked


    fun onClickDetail(choosenItem: ForViewModelWeather) {
        _choosendetail.value = choosenItem
   //  choosenItem.isSelected = true

    }



}