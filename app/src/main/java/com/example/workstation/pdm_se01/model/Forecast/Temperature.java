package com.example.workstation.pdm_se01.model.Forecast;

/**
 * Created by workstation on 31/10/2016.
 */

public class Temperature {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morning) {
        this.morn = morn;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }


}
