package com.example.workstation.pdm_se01.DAL;

/**
 * Created by workstation on 27/10/2016.
 */

public class Bulk {
    int _id;
    String name;
    String country;
    Coordenate coord;

    public int getId(){
        return _id;
    }
    public void setId(int id ){ _id = id;}

    public String getName(){ return name;}
    public void setName(String name){this.name = name;}

    public String getCountry(){ return country;}
    public void setCountry(String country){this.country = country;}

    public Coordenate getCoord(){ return coord;}
    public void setCoord(Coordenate coord){ this.coord = coord;}
}
