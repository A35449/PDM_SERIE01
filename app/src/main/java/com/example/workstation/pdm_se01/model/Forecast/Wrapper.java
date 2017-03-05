package com.example.workstation.pdm_se01.model.Forecast;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by workstation on 31/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wrapper {
    private City city;
    private List<Forecast> list;
//    private String cod;
//    private String message;
//    private int cnt;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Forecast> getList() {
        return list;
    }

//    public void setList(List<Forecast> list) {
//        this.list = list;
//    }
//
//    public int getCnt() {
//        return cnt;
//    }
//
//    public void setCnt(int cnt) {
//        this.cnt = cnt;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getCod() {
//        return cod;
//    }
//
//    public void setCod(String cod) {
//        this.cod = cod;
//    }
}
