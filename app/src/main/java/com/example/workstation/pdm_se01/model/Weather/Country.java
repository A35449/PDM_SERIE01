package com.example.workstation.pdm_se01.model.Weather;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by workstation on 27/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    String name;
//    String img;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

//    public String getImg(){
//        return img;
//    }
//
//    public void setImg(String img){
//        this.img = img;
//    }
}
