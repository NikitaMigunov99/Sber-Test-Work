package com.example.sbertestwork.data.local;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.sbertestwork.models.Weather;

import java.util.List;

import javax.inject.Inject;

public class DatabaseSource {


    private Context context;

    @Inject
    public DatabaseSource(Context context) {
        this.context = context;
    }

    public MutableLiveData<Weather> getLocalData(){
        final MutableLiveData<Weather> data = new MutableLiveData<>();
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Test", "Test New Thread getLocalData");
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "weather-database").build();
                WeatherDao dao=db.getWeatherDao();
                Weather weather= dao.getWeather();
                if(weather!=null)
                    data.postValue(weather);

            }
        });
        thread.start();
        return data;
    }


    public void updateLocalData(final Weather weather) {
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Test", "Test New Thread updateLocalData "+ weather.getCity());
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "weather-database").build();
                WeatherDao dao=db.getWeatherDao();
                dao.insert(weather);
            }
        });
        thread.start();
    }

}
