package com.example.pogoda2.repozitory_and_dataanswer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherDatabase(
    @PrimaryKey
    val id:Int,
    val cityName:String,
    val idImage: String,
    val dayToday: String,
    val sunrise: Int,
    val sunset: Int,
    val tempMorn: Double,
    val tempDay: Double,
    val tempEvening: Double,
    val description: String,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double
)

fun List<WeatherDatabase>.asDomainModel(): List<ForViewModelWeather> { // функция для преобразования DatabaseVideo объектов базы данных в объекты домена
    return map {
        ForViewModelWeather(
            id = it.id,
            cityName = it.cityName,
            idImage = it.idImage,
            daytoday = it.dayToday,
            sunrise = it.sunrise,
            sunset = it.sunset,
            tempMorn = it.tempMorn,
            tempDay = it.tempDay,
            tempEvening = it.tempEvening,
            description = it.description,
            pressure = it.pressure,
            humidity = it.humidity,
            windSpeed = it.windSpeed
        )
    }
}