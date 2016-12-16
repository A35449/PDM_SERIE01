package com.example.workstation.pdm_se01.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

import com.example.workstation.pdm_se01.activities.WeatherActivity
import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.WeatherByLocation

/**
 * Created by Tiago on 15/12/2016.
 */

class FavLocationListAdapter(internal var context: Context, resId: Int, resource: List<FavLocationModel>) : ArrayAdapter<FavLocationModel>(context, resId, resource) {
    internal var modelItems: List<FavLocationModel>? = null

    init {
        this.modelItems = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // TODO Auto-generated method stub
        val inflater = (context as Activity).layoutInflater
        convertView = inflater.inflate(R.layout.location_list_row, parent, false)
        val name = convertView!!.findViewById(R.id.textView1) as TextView
        val cb = convertView.findViewById(R.id.checkBox1) as CheckBox
        cb.setOnClickListener { modelItems!![position].check = 1 xor modelItems!![position].check }
        name.text = modelItems!![position].location
        val myIntent = Intent(context, WeatherByLocation::class.java)
        convertView.setOnClickListener(View.OnClickListener {
                context.startActivity(myIntent)
        })
        if (modelItems!![position].check == 1)
            cb.isChecked = true
        else
            cb.isChecked = false
        return convertView
    }

}