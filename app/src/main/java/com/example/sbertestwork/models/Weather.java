package com.example.sbertestwork.models;

public class Weather {

    private int temperature;
    private int windSpeed;


    public Weather() {
    }


    public Weather(int temperature, int windSpeed) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
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

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }


}
