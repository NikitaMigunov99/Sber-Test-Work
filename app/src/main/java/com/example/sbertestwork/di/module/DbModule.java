package com.example.sbertestwork.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.sbertestwork.data.local.AppDatabase;
import com.example.sbertestwork.data.local.WeatherDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "weather-database.db")
                .allowMainThreadQueries().build();
        //Github.db
    }

    @Provides
    @Singleton
    WeatherDao provideGithubDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getWeatherDao();
    }
}
