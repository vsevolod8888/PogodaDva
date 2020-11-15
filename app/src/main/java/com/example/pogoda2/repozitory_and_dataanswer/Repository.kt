package com.example.pogoda2.repozitory_and_dataanswer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import apiservise.WeatherApiService
import apiservise.retrofit
import com.example.pogoda2.dataanswer.Base
import com.example.pogoda2.dataanswer.City
import com.example.pogoda2.dataanswer.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(private val database: OurDatabase) {

   // val errorMessage = MutableLiveData<String>()
   val errorMessage = MutableLiveData<String>()

    val weather: LiveData<List<ForViewModelWeather>> =
        Transformations.map(database.weatherdao.getWeather()) {
            it.asDomainModel() //  используйте Transformations.map для преобразования списка объектов базы данных в список объектов домена
        }
    fun refreshItem(){

    }




    suspend fun refreshWeatherByCityName(city: String) {
        withContext(Dispatchers.IO) {
            val api: WeatherApiService = retrofit.create(WeatherApiService::class.java)
            var call: Response<Base>? = null
            try {
                call = api.getProperties1(q = city, cnt = 17, units = "metric", lang = "ru")
            }catch (e: Exception){
                                                                      //    ?????? 28.10.2020
           errorMessage.postValue(e.message)
            }
            val weatherlist = call?.body()
            weatherlist?.asDatabaseModel()?.let {

                database.weatherdao.insertAll(it)

            }


        }
    }
    suspend fun refreshWeather2(lat:String,lon:String) {
        withContext(Dispatchers.IO) {
            val api: WeatherApiService = retrofit.create(WeatherApiService::class.java)
            var call: Response<Base>? = null
            try {
                call = api.getProperties2(lat = lat,lon = lon,cnt = 17,units = "metric",lang = "ru")
            }catch (e: Exception){
                //    ?????? 30.10.2020
                errorMessage.postValue(e.message)
            }
            val weatherlist = call?.body()
            weatherlist?.asDatabaseModel()?.let {

                database.weatherdao.insertAll(it)

            }


        }
    }


}