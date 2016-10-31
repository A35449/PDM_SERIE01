package com.example.workstation.pdm_se01.AWA;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by workstation on 31/10/2016.
 */

public class API {

    private final String API_KEY = "3b23922cd82d67ca2db1f9d0e189dc9a";

    private final String BASE_URL = "http://api.openweathermap.org";

    private final String BASE_URL_CITY = BASE_URL +"/data/2.5/weather";

    private final String BASE_URL_FORECAST = BASE_URL + "/data/2.5/forecast/daily";

    Context context;
    RequestQueue queue;
    NetworkImageView imgView;
    private static ImageLoader loader;

    public API(Context context) {
       this.context = context;
        queue  = SingletonRequest.getInstance(context).initializeRequestQueue();
    }

    public void getWeather(String country, String city, Response.Listener<String> sucessHandler, Response.ErrorListener errHandler){
        String ps = String.format(BASE_URL_CITY + "?q=%s,%s&appid=%s",city,country,API_KEY);
        StringRequest req =  new StringRequest(ps,sucessHandler,errHandler);
        SingletonRequest.getInstance(context).addToRequestQueue(req);
    }

    public void getForecast(String city,  Response.Listener<String> sucessHandler, Response.ErrorListener errHandler){
        String ps = String.format(BASE_URL_FORECAST + "?q=%s&mode=json&units=metric&cnt=7&appid=%s",city,API_KEY);
        StringRequest req =  new StringRequest(ps,sucessHandler,errHandler);
        SingletonRequest.getInstance(context).addToRequestQueue(req);
    }

    public void getImage(String icon, NetworkImageView target){
        String imgurl = BASE_URL + "/img/w/" + icon;
        target.setImageUrl(imgurl,loader);
    }
}
