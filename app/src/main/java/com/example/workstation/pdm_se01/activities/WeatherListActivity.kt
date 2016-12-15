package com.example.workstation.pdm_se01.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Response

import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.adapter.ForecastAdapter
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.network.API
import com.example.workstation.pdm_se01.utils.Converter
import java.io.IOException

class WeatherListActivity : AppCompatActivity() {

    private var lv: ListView? = null
    private var adapter: ArrayAdapter<Forecast>? = null
    private var api: API? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_list)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)





        lv = findViewById(R.id.day_forecast_list) as ListView



        var repHandler = Response.Listener<String>(){response ->

            try{
                var wrap = Converter.convertToForecast(response)
                fillerList(wrap.list)
            }catch(ex: IOException){
                System.out.print(ex.message)
            }
        }

        val errHandler = Response.ErrorListener(){error->
            Toast.makeText(this, error.message,Toast.LENGTH_LONG).show()
        }






        if (adapter != null) {
            lv?.adapter = adapter

            api = API(this.applicationContext)
            var local=intent.getStringExtra("locationString").toString().split(",")
            api?.getForecast(local[0], repHandler, errHandler)

        }


}

    private fun  fillerList(list: List<Forecast>) {
        adapter = ForecastAdapter(this, R.layout.forecast_item, list)
        lv!!.adapter = adapter
    }
    }
