package com.example.workstation.pdm_se01.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.model.Weather.Weather

/**
 * Created by Jos on 16/12/2016.
 */

class WeatherAdpater( context: Context, _layoutResId: Int, _data: List<Weather>) : ArrayAdapter<Weather>(context,_layoutResId,_data){

    val data : List<Weather>
    val layoutId : Int

    init{
        data = _data
        layoutId = _layoutResId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        throw Exception()
    }
}