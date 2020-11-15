package com.example.pogoda2.repozitory_and_dataanswer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherDatabase::class], version = 3)
abstract class OurDatabase : RoomDatabase(){
    abstract val weatherdao: WeatherDao
}
private lateinit var INSTANCE: OurDatabase             // переменная INSTANCE для хранения одноэлементного объекта БД (чтобы не открылись неск.экз.бд)
fun getDatabase(context: Context): OurDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OurDatabase::class.java,
                "videos").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}