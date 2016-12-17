package com.example.workstation.pdm_se01.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.WeatherByLocation
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.model.HomeActivityWeather.HomeActivityWeatherModel
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.squareup.picasso.Picasso

/**
 * Created by Tiago on 16/12/2016.
 */
class HomeActivityListAdapter(internal var context:
        Context, internal var layoutResId: Int, data: List<HomeActivityWeatherModel>) : ArrayAdapter<HomeActivityWeatherModel>(context, layoutResId, data) {
    internal var data: List<HomeActivityWeatherModel>? = null

    init {
        this.data = data


    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: WeatherHolder? = null

        holder = WeatherHolder()

        holder.imageIcon = convertView!!.findViewById(R.id.weatherImage) as ImageView
        holder.information= convertView!!.findViewById(R.id.informationText) as TextView

        // TODO Auto-generated method stub
        val inflater = (context as Activity).layoutInflater
        convertView= inflater.inflate(R.layout.home_activity_weather_row,parent,true )


        val  forecast=data!![position].weatherToday

        val  allInfo="Local :"+  data!![position].location+" \n"+
                "Min: " + data!![position].weatherToday.temp.min+"ºC\n"+
                "Max: "+ data!![position].weatherToday.temp.max+"ºC\n"





        holder.information?.text=allInfo;
     //   val iconWeather=convertView!!.findViewById(R.id.weatherImage)as ImageView












       Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into(holder.imageIcon)





        convertView.setOnClickListener{(View.OnClickListener {

            val sync = Syncronizer(context.applicationContext, API_Forecast(context.applicationContext))
            val parsedlocation = data!![position].location.split(",")
            val query = QueryRegist(parsedlocation[0], parsedlocation[1])
            sync.syncronizeSearch(query)

            val myIntent = Intent(context, WeatherByLocation::class.java)
            myIntent.putExtra("location", data!![position].location)
            context.startActivity(myIntent)


        })}

        return convertView







    }

    internal class WeatherHolder {
        var imageIcon: ImageView? = null
        var information :TextView?=null

    }
}