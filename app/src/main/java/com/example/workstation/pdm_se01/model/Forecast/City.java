package com.example.workstation.pdm_se01.model.Forecast;

import com.example.workstation.pdm_se01.model.Weather.Coordenate;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by workstation on 31/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    private int id;
    private String name;
    private Coordenate coord;
    private String country;
//    private int population;
//    private String iso2;
//    private String type;
//    private double geoname_id;
//    private double lat;
//    private double lon;
//

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

//    public Coordenate getCoord() {
//        return coord;
//    }
//
//    public void setCoord(Coordenate coord) {
//        this.coord = coord;
//    }

//    public int getPopulation() {
//        return population;
//    }
//
//    public void setPopulation(int population) {
//        this.population = population;
//    }

//    public double getGeoname_id() {
//        return geoname_id;
//    }
//    public void setGeoname_id(double geoname_id) {
//        this.geoname_id = geoname_id;
//    }
//
//    public double getLat() {
//        return lat;
//    }
//
//    public void setLat(double lat) {
//        this.lat = lat;
//    }
//
//    public double getLon() {
//        return lon;
//    }
//
//    public void setLon(double lon) {
//        this.lon = lon;
//    }
//
//    public String getIso2() {
//        return iso2;
//    }
//
//    public void setIso2(String iso2) {
//        this.iso2 = iso2;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
}
