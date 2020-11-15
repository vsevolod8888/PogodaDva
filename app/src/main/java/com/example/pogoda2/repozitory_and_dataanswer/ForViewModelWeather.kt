package com.example.pogoda2.repozitory_and_dataanswer

import java.util.*

data class ForViewModelWeather(
    val id:Int,
    val isSelected:Boolean = false,
    val cityName:String,
    val idImage: String,
    val daytoday:String,
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