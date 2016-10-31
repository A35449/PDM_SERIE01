package com.example.workstation.pdm_se01.DAL.Forecast;

import com.example.workstation.pdm_se01.DAL.Weather.Coordenate;

/**
 * Created by workstation on 31/10/2016.
 */

public class City {
    private int id;
    private String name;
    private Coordenate coord;
    private String country;
    private int population;

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coordenate getCoord() {
        return coord;
    }

    public void setCoord(Coordenate coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
