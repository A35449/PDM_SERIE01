package com.example.workstation.pdm_se01.utils;

import com.example.workstation.pdm_se01.model.Weather.Wrapper;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

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

    public static com.example.workstation.pdm_se01.model.Forecast.Wrapper convertToForecast(String json) throws IOException{
        byte[] resp_bytes = json.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        com.example.workstation.pdm_se01.model.Forecast.Wrapper wrap = objectMapper.readValue(resp_bytes, com.example.workstation.pdm_se01.model.Forecast.Wrapper.class);
        return wrap;
    }
}
