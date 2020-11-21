package com.example.pogoda2.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.pogoda2.repozitory_and_dataanswer.ForViewModelWeather
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("SimpleDateFormat")
@BindingAdapter("ourTextDateToday")
fun TextView.setSleepDurationFormatted(list: ForViewModelWeather?): CharSequence? {
    list?.let {
        val dayToday: Int = list.sunrise
        val date = Date(dayToday * 1000L)
        val locale = Locale("ru", "RU")
        val format2: DateFormat = SimpleDateFormat("EEE, dd.MM", locale)          //**************************************
        val daytoday: String = format2.format(date).capitalize()
        text = daytoday
    }
    return text
}

@SuppressLint("SetTextI18n")
@BindingAdapter("tvTemperature")
fun TextView.setSleepQualityString(item: ForViewModelWeather?) {
    item?.let {
        val ggg:Double = item.tempDay
        val result = ggg.roundToInt()
        text = "$result °C"
    }
}

@BindingAdapter("timeSunrise")                                            // рассвет
fun TextView.setTimeSunrise(item: ForViewModelWeather?) {
    item?.let {
        val sunriseTime: Int = item.sunrise
        val date = Date(sunriseTime * 1000L)
        val format: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formatted: String = format.format(date)
        text = formatted
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("dayToday")                                            // дата
fun TextView.setDayToday(item: ForViewModelWeather?) {
    item?.let {
        val sunriseTime: Int = item.sunrise
        val locale = Locale("ru", "RU")
        val date = Date(sunriseTime * 1000L)
        val format2: DateFormat = SimpleDateFormat(("EEEE, dd MMMM"), locale)
        val daytoday: String = format2.format(date).capitalize()
        text = daytoday
    }
}

@BindingAdapter("temperatureMorning")                                            // темп. утро
fun TextView.setTemperatureMorn(item: ForViewModelWeather?) {
    item?.let {
        val ggg:Double = item.tempMorn
        val result = ggg.roundToInt()
        text = "$result°"
    }
}

@BindingAdapter("temperatureDay")                                                 // темп. день
fun TextView.setTemperatureDay(item: ForViewModelWeather?) {
    item?.let {
        val ggg:Double = item.tempDay
        val result = ggg.roundToInt()
        text = "$result°"
    }
}

@BindingAdapter("temperatureEvening")                                            // темп. вечер
fun TextView.setTemperatureEvening(item: ForViewModelWeather?) {
    item?.let {
        val ggg:Double = item.tempEvening
        val result = ggg.roundToInt()
        text = "$result°"
    }
}

@BindingAdapter("timeSunset")                                            // время закат
fun TextView.setTimeSunset(item: ForViewModelWeather?) {
    item?.let {
        val locale = Locale("ru", "RU")
        val format: DateFormat = SimpleDateFormat("HH:mm", locale)
        val sunSetTime: Int = item.sunset
        val date2 = Date(sunSetTime * 1000L)
        val formatted2: String = format.format(date2)
        text = formatted2
    }
}

@BindingAdapter("weatherDescription")                                            // описание погоды
fun TextView.setWeatherDescription(item: ForViewModelWeather?) {
    item?.let {
        text = item.description.capitalize()
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("windSpeed")                                            // скорсть ветра
fun TextView.setWindSpeed(item: ForViewModelWeather?) {
    item?.let {

        text = item.windSpeed.toString() + " м/с"
    }
}

@BindingAdapter("humidity")                                            // влажность
fun TextView.setHumidity(item: ForViewModelWeather?) {
    item?.let {
        text = item.humidity.toString() + " %"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("pressure")                                            // давление
fun TextView.setPressure(item: ForViewModelWeather?) {
    item?.let {
        val b:Int = item.pressure
        val c:Int = ((b*735.559)/1000).roundToInt()
        text = "$c мм.рт.ст."
    }
}



@BindingAdapter("imageFromInterneeeet")
fun setBackgroundImage(view: ImageView, item: ForViewModelWeather?) {
    val imageUrl: String =
    when (item?.idImage) {
        "01d" -> "https://openweathermap.org/img/wn/01d@4x.png"
        "01n" -> "https://openweathermap.org/img/wn/01n@4x.png"
        "02d" -> "https://openweathermap.org/img/wn/02d@4x.png"
        "02n" -> "https://openweathermap.org/img/wn/02n@4x.png"
        "03d" -> "https://openweathermap.org/img/wn/03d@4x.png"
        "03n" -> "https://openweathermap.org/img/wn/03n@4x.png"
        "04d" -> "https://openweathermap.org/img/wn/04d@4x.png"
        "04n" -> "https://openweathermap.org/img/wn/04n@4x.png"
        "05d" -> "https://openweathermap.org/img/wn/05d@4x.png"
        "05n" -> "https://openweathermap.org/img/wn/05n@4x.png"
        "06d" -> "https://openweathermap.org/img/wn/06d@4x.png"
        "06n" -> "https://openweathermap.org/img/wn/06n@4x.png"
        "07d" -> "https://openweathermap.org/img/wn/07d@4x.png"
        "07n" -> "https://openweathermap.org/img/wn/07n@4x.png"
        "08d" -> "https://openweathermap.org/img/wn/08d@4x.png"
        "08n" -> "https://openweathermap.org/img/wn/08n@4x.png"
        "09d" -> "https://openweathermap.org/img/wn/09d@4x.png"
        "09n" -> "https://openweathermap.org/img/wn/09n@4x.png"
        "10d" -> "https://openweathermap.org/img/wn/10d@4x.png"
        "10n" -> "https://openweathermap.org/img/wn/10n@4x.png"
        "11d" -> "https://openweathermap.org/img/wn/11d@4x.png"
        "11n" -> "https://openweathermap.org/img/wn/11n@4x.png"
        "13d" -> "https://openweathermap.org/img/wn/13d@4x.png"
        "13n" -> "https://openweathermap.org/img/wn/13n@4x.png"
        "50d" -> "https://openweathermap.org/img/wn/50d@4x.png"
        "50n" -> "https://openweathermap.org/img/wn/50n@4x.png"
        else ->""
    }
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}


