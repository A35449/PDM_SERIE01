package com.example.workstation.pdm_se01.Utils;

import com.example.workstation.pdm_se01.DAL.Weather.Wrapper;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import static android.icu.lang.UCharacter.JoiningGroup.DAL;

/**
 * Created by workstation on 31/10/2016.
 */

public class Converter {

    public static Wrapper convertToWeather(String json) throws IOException{
        byte[] resp_bytes = json.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        Wrapper wrap = objectMapper.readValue(resp_bytes, Wrapper.class);
        return wrap;
    }

    public static com.example.workstation.pdm_se01.DAL.Forecast.Wrapper convertToForecast(String json) throws IOException{
        byte[] resp_bytes = json.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        com.example.workstation.pdm_se01.DAL.Forecast.Wrapper wrap = objectMapper.readValue(resp_bytes, com.example.workstation.pdm_se01.DAL.Forecast.Wrapper.class);
        return wrap;
    }
}
