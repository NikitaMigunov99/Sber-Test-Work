package com.example.sbertestwork.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sbertestwork.models.Weather;
import com.example.sbertestwork.repo.WeatherRepo;

public class MainViewModel extends ViewModel {


    private WeatherRepo weatherRepo= new WeatherRepo();
    private LiveData<Weather> weather;


    public LiveData<Weather> getData() {
        return weather;
    }

    public void getMyLocationWeather(double lon, double lat) {

            weather=weatherRepo.getWeather(lon, lat);
            Log.d("test","weatherRepo.getWeather() getMyLocationWeather");
    }

    public void getCityWeather(String city){
        weather= weatherRepo.getWeather(city);
        Log.d("test","weatherRepo.getWeather() getCityWeather");
    }

}
