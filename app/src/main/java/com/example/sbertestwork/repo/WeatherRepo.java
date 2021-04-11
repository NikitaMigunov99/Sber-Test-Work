package com.example.sbertestwork.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sbertestwork.models.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepo {


    private final MutableLiveData<Weather> weather = new MutableLiveData<>();
    private final String API_KEY="95d22f54d7631cbf8a42c0ca17bc6e91";



    private void getMyLocationWeather(double lon, double lat) {

        Log.d("Test", "WeatherRepo getMyLocationWeather");
        APIRepo apiRepo = new APIRepo();
        apiRepo.getLocalWeatherRepo().getMyLocationWeather(lon,lat,API_KEY).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.body() != null) {
                    Log.d("Test", "makeRequest() weather.postValue(responseProcessing(response))");
                    weather.postValue(responseProcessing(response));

                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d("Test", "makeRequest() Failed to loud data " + t.getMessage(), t);
                t.printStackTrace();
            }
        });
    }

    private void getCityWeather(String city) {

        Log.d("Test", "WeatherRepo getCityWeather");
        APIRepo apiRepo = new APIRepo();
        apiRepo.getLocalWeatherRepo().getCityWeather(city,API_KEY).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.body() != null) {
                    Log.d("Test", "makeRequest() weather.postValue(responseProcessing(response))");
                    weather.postValue(responseProcessing(response));

                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d("Test", "makeRequest() Failed to loud data " + t.getMessage(), t);
                t.printStackTrace();
            }
        });
    }

    private Weather responseProcessing(Response<APIResponse> response){
        Weather weather= new Weather();
        weather.setTemperature((int) (response.body().getMain().getTemp()-273f));
        weather.setWindSpeed((int) response.body().getWind().getSpeed());
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
