package com.example.pogoda2.dataanswer

import android.app.Application
import com.example.pogoda2.repozitory_and_dataanswer.Repository
import com.example.pogoda2.repozitory_and_dataanswer.WeatherDatabase
import com.example.pogoda2.repozitory_and_dataanswer.getDatabase
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.List

data class List(
	@SerializedName("id") val id: Int,
	@SerializedName("dt") val dt: Int,
	@SerializedName("sunrise") val sunrise: Int,
	@SerializedName("sunset") val sunset: Int,
	@SerializedName("temp") val temp: Temp,
	@SerializedName("feels_like") val feels_like: Feels_like,
	@SerializedName("pressure") val pressure: Int,
	@SerializedName("humidity") val humidity: Int,
	@SerializedName("weather") val weather: List<Weather>,
	@SerializedName("speed") val speed: Double,
	@SerializedName("deg") val deg: Int,
	@SerializedName("clouds") val clouds: Int,
	@SerializedName("pop") val pop: Double,
	@SerializedName("rain") val rain: Double
)


fun Base.asDatabaseModel(): List<WeatherDatabase> {
    val answerlist = mutableListOf<WeatherDatabase>()
	//val base: Base? =null
    list.forEachIndexed { index, it ->

		answerlist.add(
				WeatherDatabase(
					id = index,
					cityName = this.city.name,
					idImage = it.weather[0].icon,
					sunrise = it.sunrise,
					sunset = it.sunset,
					tempMorn = it.temp.morn,
					tempDay = it.temp.day,
					tempEvening = it.temp.eve,
					pressure = it.pressure,
					windSpeed = it.speed,
					humidity = it.humidity,
					dayToday = formatting(it.sunrise),
					description = it.weather[0].description

				)
			)
		}

    return answerlist
}


fun formatting(sunrise: Int): String {
    val date = Date(sunrise * 1000L)
    val format: DateFormat = SimpleDateFormat(("yyyy-MM-dd"))
    val daytoday: String = format.format(date)
    return daytoday

}