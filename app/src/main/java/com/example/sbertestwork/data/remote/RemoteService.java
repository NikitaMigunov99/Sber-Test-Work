package com.example.sbertestwork.data.remote;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteService {



    //private static CurrencyApi currencyApi; https://api.openweathermap.org
    private static LocalWeatherApi localWeatherApi;


    public RemoteService() {
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
