package com.example.workstation.pdm_se01.activities

import kotlin.jvm.javaClass
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView

import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.R
import com.squareup.picasso.Picasso

import org.codehaus.jackson.map.ObjectMapper

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val i = intent
        val date = i.getSerializableExtra("Date") as Date

        val mapper = ObjectMapper()
        var forecast = Forecast()
        try {
            //forecast = mapper.readValue(i.getStringExtra("forecastJSON"), Forecast::class.java )
        } catch (e: IOException) {
            //e.printStackTrace()
        }

        //fillActivity(forecast, date)
    }

    private fun fillActivity(forecast: Forecast, date: Date) {
        val sdf = SimpleDateFormat.getDateInstance() as SimpleDateFormat
        supportActionBar!!.title = sdf.format(date)

        val myIcon = findViewById(R.id.weatherImage) as ImageView
        Picasso.with(applicationContext).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into(myIcon)
        val minTemperature = findViewById(R.id.minTemperatureWeather) as TextView
        minTemperature.text = java.lang.Double.toString(forecast.temp.min)
        val maxTemperature = findViewById(R.id.maxTemperatureWeather) as TextView
        maxTemperature.text = java.lang.Double.toString(forecast.temp.max)
        val wind_speed = findViewById(R.id.windSpeedWeather) as TextView
        wind_speed.text = java.lang.Double.toString(forecast.speed)
        val pressure = findViewById(R.id.pressure) as TextView
        pressure.text = java.lang.Double.toString(forecast.pressure)
        val humidity = findViewById(R.id.humidity) as TextView
        humidity.text = java.lang.Double.toString(forecast.humidity.toDouble())
    }
}
