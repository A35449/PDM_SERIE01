package com.example.workstation.pdm_se01.model.Weather;

/**
 * Created by workstation on 25/10/2016.
 */

public class Sys {

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }

    private double message;
    private String country;
    private double sunrise;
    private double sunset;
}
