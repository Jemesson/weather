package com.jemessonlima.weather.forecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jemessonlima.weather.forecast.model.CityDatabase

@Dao
interface CityDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cityDatabase: CityDatabase)

    @Query("SELECT * FROM citydatabase ORDER BY cityName ASC")
    fun getAllCitiesDatabase(): List<CityDatabase>
}
