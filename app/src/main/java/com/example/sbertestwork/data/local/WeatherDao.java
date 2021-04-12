package com.example.sbertestwork.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sbertestwork.models.Weather;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(Weather weather);

    @Query("SELECT * FROM weather LIMIT 1")
    Weather getWeather();
}
