package com.jemessonlima.weather.forecast.manager

import com.jemessonlima.weather.forecast.service.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherManager {
    companion object {
        const val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

    private val instance = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getOpenWeatherService(): WeatherService = instance.create(WeatherService::class.java)
}
