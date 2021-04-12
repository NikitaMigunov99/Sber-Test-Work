package com.example.sbertestwork.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalWeatherApi {

    @GET("/data/2.5/weather")
    Call<RemoteResponse> getMyLocationWeather(@Query("lon") double lon, @Query("lat") double lat, @Query("appid") String key);

    @GET("/data/2.5/weather")
    Call<RemoteResponse> getCityWeather(@Query("q") String city, @Query("appid") String key);
}
