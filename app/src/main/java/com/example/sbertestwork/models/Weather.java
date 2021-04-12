package com.example.sbertestwork.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Weather {

    @PrimaryKey
    @NonNull
    private String city;
    private int temperature;
    private float windSpeed;
    private String time;

    public Weather() {
    }

    public Weather(int temperature, float windSpeed, String city) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.city= city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
