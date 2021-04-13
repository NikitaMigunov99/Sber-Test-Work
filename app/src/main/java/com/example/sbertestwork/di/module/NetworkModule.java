package com.example.sbertestwork.di.module;

import android.util.Log;

import com.example.sbertestwork.data.remote.LocalWeatherApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {


    @Provides
    public LocalWeatherApi getLocalWeatherRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("test","weatherRepo.getWeather()");

        return retrofit.create(LocalWeatherApi.class);
    }
}
