package com.jemessonlima.weather.forecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jemessonlima.weather.forecast.dao.CityDatabaseDao
import com.jemessonlima.weather.forecast.model.CityDatabase

@Database(entities = [CityDatabase::class], version = 1, exportSchema = false)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun cityDatabaseDao(): CityDatabaseDao

    companion object {
        private var INSTANCE: WeatherAppDatabase? = null

        fun getInstance(context: Context): WeatherAppDatabase? {
            if (INSTANCE == null) {
                synchronized(WeatherAppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WeatherAppDatabase::class.java, "city_weather.db"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }
    }
}
