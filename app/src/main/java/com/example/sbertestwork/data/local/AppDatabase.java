package com.example.sbertestwork.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sbertestwork.models.Weather;

@Database(entities = Weather.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();

}