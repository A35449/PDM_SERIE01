package com.example.workstation.pdm_se01.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.WeatherActivity
import com.squareup.picasso.Picasso

import org.codehaus.jackson.map.ObjectMapper

import java.io.IOException
import java.lang.Double
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ForecastAdapter(internal var context:

                      Context, internal var layoutResId: Int, data: List<Forecast>) : ArrayAdapter<Forecast>(context, layoutResId, data) {
    internal var data: List<Forecast>? = null

    init {
        this.data = data
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ForecastHolder? = null

        if (convertView == null) {
            val inflater = (context as Activity).layoutInflater
            convertView = inflater.inflate(layoutResId, parent, false)

            holder = ForecastHolder()
            holder.imageIcon = convertView!!.findViewById(R.id.iconWeather) as ImageView
            holder.day = convertView.findViewById(R.id.day) as TextView
            holder.minTemperature = convertView.findViewById(R.id.minTemperature) as TextView
            holder.windSpeed = convertView.findViewById(R.id.windSpeed) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ForecastHolder
        }

        val forecast = data!![position]
        holder.minTemperature!!.text = java.lang.Double.toString(forecast.temp.min)
        holder.minTemperature!!.append(" ºC")
        holder.windSpeed!!.text = java.lang.Double.toString(forecast.speed)
        holder.windSpeed!!.append("m/s")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, position)
        val date = calendar.time
        val sdf = SimpleDateFormat.getDateInstance() as SimpleDateFormat
        holder.day!!.text = sdf.format(date)
        Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into(holder.imageIcon)

        convertView.setOnClickListener(View.OnClickListener {
            val mapper = ObjectMapper()
            val myIntent = Intent(context, WeatherActivity::class.java)
            var forecastJSON: String? = null
            try {
                forecastJSON = mapper.writeValueAsString(forecast)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            myIntent.putExtra("forecastJSON", forecastJSON) //Optional parameters
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, position)
            val date = calendar.time
            myIntent.putExtra("Date", date)
            context.startActivity(myIntent)
        })
        return convertView
    }

    internal class ForecastHolder {
        var imageIcon: ImageView? = null
        var day: TextView? = null
        var minTemperature: TextView? = null
        var windSpeed: TextView? = null
    }
}