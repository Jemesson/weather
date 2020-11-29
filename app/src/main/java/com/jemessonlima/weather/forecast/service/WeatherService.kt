package com.jemessonlima.weather.forecast.service

import com.jemessonlima.weather.forecast.model.City
import com.jemessonlima.weather.forecast.model.Root
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

// Example.: http://api.openweathermap.org/data/2.5/weather?q=Recife&APPID=MY_APP_ID
interface WeatherService {
    companion object {
        const val APP_ID = "3e022e7b6e2c47156df35372c411b977"
    }

    @GET("weather")
    fun getCityWeather(
        @Query("q") cityName: String,
        @Query("APPID") appId: String = APP_ID
    ): Call<City>

    @GET("find")
    fun findTemperatures(
        @Query("q") cityName: String,
        @Query("units") units: String = "metrics",
        @Query("APPID") appId: String = APP_ID
    ): Call<Root>
}
