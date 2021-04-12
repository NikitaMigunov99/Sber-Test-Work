package com.example.sbertestwork.data.remote;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.sbertestwork.models.Weather;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRemoteSource {


    private final MutableLiveData<Weather> weather = new MutableLiveData<>();
    private final String API_KEY="95d22f54d7631cbf8a42c0ca17bc6e91";


    private void getMyLocationWeather(double lon, double lat) {

        Log.d("Test", "WeatherRepo getMyLocationWeather");
        RemoteService remoteService = new RemoteService();
        remoteService.getLocalWeatherRepo().getMyLocationWeather(lon,lat,API_KEY).enqueue(new Callback<RemoteResponse>() {
            @Override
            public void onResponse(Call<RemoteResponse> call, Response<RemoteResponse> response) {
                if (response.body() != null) {
                    Log.d("Test", "makeRequest() weather.postValue(responseProcessing(response))");
                    weather.postValue(responseProcessing(response));

                }
            }

            @Override
            public void onFailure(Call<RemoteResponse> call, Throwable t) {
                Log.d("Test", "makeRequest() Failed to loud data " + t.getMessage(), t);
                t.printStackTrace();
            }
        });
    }

    private void getCityWeather(String city) {

        Log.d("Test", "WeatherRepo getCityWeather");
        RemoteService remoteService = new RemoteService();
        remoteService.getLocalWeatherRepo().getCityWeather(city,API_KEY).enqueue(new Callback<RemoteResponse>() {
            @Override
            public void onResponse(Call<RemoteResponse> call, Response<RemoteResponse> response) {
                if (response.body() != null) {
                    Log.d("Test", "makeRequest() weather.postValue(responseProcessing(response))");
                    weather.postValue(responseProcessing(response));

                }
            }

            @Override
            public void onFailure(Call<RemoteResponse> call, Throwable t) {
                Log.d("Test", "makeRequest() Failed to loud data " + t.getMessage(), t);
                t.printStackTrace();
            }
        });
    }


    private Weather responseProcessing(Response<RemoteResponse> response){
        Weather weather= new Weather();
        weather.setTemperature((int) (response.body().getMain().getTemp()-273f));
        weather.setWindSpeed(response.body().getWind().getSpeed());
        weather.setCity(response.body().getName());

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDate = simpleDateFormat.format(date);
        weather.setTime(currentDate);

        return weather;
    }

    public MutableLiveData<Weather> getWeather(double lon, double lat) {
        Log.d("Test", "MutableLiveData<Weather> getWeather() Location");
        getMyLocationWeather(lon, lat);
        return weather;
    }
    public MutableLiveData<Weather> getWeather(String city) {
        Log.d("Test", "MutableLiveData<Weather> getWeather() City");
        getCityWeather(city);
        return weather;
    }

}
