package com.jemessonlima.weather.forecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityDatabase (
        @PrimaryKey val id: Int,
        val cityName: String
)
