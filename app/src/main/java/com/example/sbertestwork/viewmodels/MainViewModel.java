package com.example.sbertestwork.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sbertestwork.data.Repository;
import com.example.sbertestwork.models.Weather;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private LiveData<Weather> weather;
    private Repository repository;
    private boolean isAccessFineLocationGranted;

    public boolean isAccessFineLocationGranted() {
        return isAccessFineLocationGranted;
    }

    public void setAccessFineLocationGranted(boolean accessFineLocationGranted) {
        isAccessFineLocationGranted = accessFineLocationGranted;
    }


    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }


    public LiveData<Weather> getData() {
        return weather;
    }

    public void getMyLocationWeather(double lon, double lat) {
        weather= repository.getRemoteMyLocationWeather(lon, lat);
        Log.d("test","weatherRepo.getWeather() getMyLocationWeather");
    }

    public void getCityWeather(String city){
        weather= repository.getRemoteCityWeather(city);
        Log.d("test","weatherRepo.getWeather() getCityWeather");
    }

    public void getLocalData(){
        weather=repository.getLocalData();
    }

    public void saveIntoDataBase(Weather weather){
        repository.saveDataIntoDataBase(weather);
    }

}
