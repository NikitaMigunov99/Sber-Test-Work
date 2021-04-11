package com.example.sbertestwork.repo;

import android.util.Log;

import com.example.sbertestwork.api.LocalWeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepo {



    //private static CurrencyApi currencyApi; https://api.openweathermap.org
    private static LocalWeatherApi localWeatherApi;


    public APIRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("test","weatherRepo.getWeather()");
        localWeatherApi= retrofit.create(LocalWeatherApi.class);
    }

    public LocalWeatherApi getLocalWeatherRepo() {
        return localWeatherApi;
    }
}
