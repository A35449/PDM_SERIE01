package com.example.workstation.pdm_se01.model.HomeActivityWeather;

import com.example.workstation.pdm_se01.model.Forecast.Forecast;

import java.util.List;

/**
 * Created by Tiago on 16/12/2016.
 */

public class HomeActivityWeatherModel {
    private String location;
    private Forecast weatherToday ;


    public Forecast getWeatherToday() {
        return weatherToday;
    }

    public void setWeatherToday(Forecast weatherToday) {
        this.weatherToday = weatherToday;
    }

    public String getLocation(){
        return this.location;

    }
    public void setLocation(String location) {
        this.location = location;

    }



}
