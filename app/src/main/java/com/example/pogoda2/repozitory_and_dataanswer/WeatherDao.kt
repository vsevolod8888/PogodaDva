package com.example.pogoda2.repozitory_and_dataanswer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("select * from weatherdatabase")
    fun getWeather(): LiveData<List<WeatherDatabase>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<WeatherDatabase>)
}