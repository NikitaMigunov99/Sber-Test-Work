package com.example.sbertestwork.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.sbertestwork.data.local.DatabaseSource;
import com.example.sbertestwork.data.remote.WeatherRemoteSource;
import com.example.sbertestwork.models.Weather;

import javax.inject.Inject;


public class Repository {


    private WeatherRemoteSource remoteSource;
    private DatabaseSource databaseSource;

    @Inject
    public Repository(WeatherRemoteSource remoteSource, DatabaseSource databaseSource) {
        this.remoteSource = remoteSource;
        this.databaseSource = databaseSource;
    }

    public LiveData<Weather> getRemoteCityWeather( String city){
        return remoteSource.getWeather(city);
    }

    public LiveData<Weather> getRemoteMyLocationWeather( double lon, double lat ){
        return remoteSource.getWeather(lon, lat);
    }

    public LiveData<Weather> getLocalData(){
        return databaseSource.getLocalData();
    }

    public void saveDataIntoDataBase(Weather weather){
        databaseSource.updateLocalData(weather);
    }

}
