package com.example.workstation.pdm_se01.model.Weather;

import java.util.List;

/**
 * Created by workstation on 25/10/2016.
 */

public class Wrapper {
    public Coordenate getCoord() {
        return coord;
    }

    public void setCoord(Coordenate coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainObj getMain() {
        return main;
    }

    public void setMain(MainObj main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    private Coordenate coord;
    private List<Weather> weather;
    private String base;
    private MainObj main;
    private Wind wind;
    private Clouds clouds;
    private double dt;
    private Sys sys;
    private double id;
    private String name;
    private int cod;
}
