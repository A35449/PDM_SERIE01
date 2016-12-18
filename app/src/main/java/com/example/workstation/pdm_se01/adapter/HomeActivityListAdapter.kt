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
import com.example.workstation.pdm_se01.model.Forecast.Wrapper

import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.squareup.picasso.Picasso

/**
 * Created by Tiago on 16/12/2016.
 */
class HomeActivityListAdapter(internal var context:
        Context, internal var layoutResId: Int, data: List<Wrapper>) : ArrayAdapter<Wrapper>(context, layoutResId, data) {
    internal var data: List<Wrapper>? = null

    init {
        this.data = data


    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: WeatherHolder? = null
        val inflater = (context as Activity).layoutInflater
        convertView= inflater.inflate(R.layout.home_activity_weather_row,parent,false )


        holder = WeatherHolder()

        holder.imageIcon = convertView!!.findViewById(R.id.weatherImage) as ImageView
        holder.information= convertView!!.findViewById(R.id.informationText) as TextView



        val  weather=data!![position].list[0].weather[0]

        val  allInfo="Local :"+  data!![position].city.name+","+data!![position].city.country +" \n"+
                "Min: " + data!![position].list[0].temp.min+"ºC\n"+
                "Max: "+ data!![position].list[0].temp.max+"ºC\n"

       holder.information?.text=allInfo;



        val myPicasso = Picasso.with(context)
        myPicasso.setIndicatorsEnabled(true)
        myPicasso.load("http://openweathermap.org/img/w/" + weather.icon + ".png").into(holder.imageIcon)







        convertView.setOnClickListener(View.OnClickListener {


            val myIntent = Intent(context, WeatherByLocation::class.java)
            myIntent.putExtra("location", data!![position].city.name + ","+ data!![position].city.country)
            context.startActivity(myIntent)


        })

        return convertView


    }

    internal class WeatherHolder {
        var imageIcon: ImageView? = null
        var information :TextView?=null

    }
}