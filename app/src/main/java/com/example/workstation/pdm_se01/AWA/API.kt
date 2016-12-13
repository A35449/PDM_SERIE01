package com.example.workstation.pdm_se01.AWA

import android.content.Context

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest

import java.util.Locale

/**
 * Created by workstation on 31/10/2016.
 */

class API(internal var context: Context) {

    private val API_KEY = "3b23922cd82d67ca2db1f9d0e189dc9a"

    private val BASE_URL = "http://api.openweathermap.org"

    private val BASE_URL_CITY = BASE_URL + "/data/2.5/weather"

    private val BASE_URL_FORECAST = BASE_URL + "/data/2.5/forecast/daily"

    private val lang: String
    internal var queue: RequestQueue
    internal var imgView: NetworkImageView? = null

    init {
        lang = Locale.getDefault().language
        queue = SingletonRequest.getInstance(context).initializeRequestQueue()
    }

    fun getWeather(country: String, city: String, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener) {
        val ps = String.format(BASE_URL_CITY + "?q=%s,%s&appid=%s&lang=%s", city, country, API_KEY, lang)
        val req = StringRequest(ps, sucessHandler, errHandler)
        SingletonRequest.getInstance(context).addToRequestQueue(req)
    }

    fun getForecast(city: String, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener) {
        val ps = String.format(BASE_URL_FORECAST + "?q=%s&mode=json&units=metric&cnt=7&appid=%s&lang=%s", city, API_KEY, lang)
        val req = StringRequest(ps, sucessHandler, errHandler)
        SingletonRequest.getInstance(context).addToRequestQueue(req)
    }

    fun getImage(icon: String, target: NetworkImageView) {
        val imgurl = BASE_URL + "/img/w/" + icon
        target.setImageUrl(imgurl, loader)
    }

    companion object {
        private val loader: ImageLoader? = null
    }
}