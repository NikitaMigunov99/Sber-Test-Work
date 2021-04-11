package com.example.sbertestwork.api;

import com.example.sbertestwork.repo.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalWeatherApi {

    @GET("/data/2.5/weather")
    Call<APIResponse> getMyLocationWeather(@Query("lon") double lon, @Query("lat") double lat, @Query("appid") String key);

    @GET("/data/2.5/weather")
    Call<APIResponse> getCityWeather(@Query("q") String city, @Query("appid") String key);
}
