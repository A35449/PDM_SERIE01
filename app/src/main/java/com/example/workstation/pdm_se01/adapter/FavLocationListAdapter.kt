package com.example.workstation.pdm_se01.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

import com.example.workstation.pdm_se01.activities.WeatherActivity
import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.WeatherByLocation
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.network.api.API_Weather
import com.example.workstation.pdm_se01.utils.QueryRegist
import java.util.*

/**
 * Created by Tiago on 15/12/2016.
 */

class FavLocationListAdapter(context: Context, resId: Int, resource: List<FavLocationModel>) : ArrayAdapter<FavLocationModel>(context, resId, resource) {
     var modelItems: List<FavLocationModel>

    companion object{
        internal var checkBoxState : BooleanArray ?= null
    }

    init {
        this.modelItems = resource
        checkBoxState = BooleanArray(resource.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var rowView = convertView
        // TODO Auto-generated method stub

        val inflater = (context as Activity).layoutInflater
        rowView = inflater.inflate(R.layout.location_list_row, parent, false)

        val textentry = modelItems!![position].location
        val txtBox = rowView?.findViewById(R.id.textView1) as TextView
        txtBox.text = textentry

        val cb = rowView?.findViewById(R.id.checkBox1) as CheckBox
        val stateChk = checkBoxState!![position]
        cb.setChecked(stateChk)
        cb.isChecked = stateChk
        cb.setOnClickListener { v ->

            val chk = v as CheckBox
            val isChecked = chk.isChecked
            checkBoxState!![position] = isChecked
            if(isChecked)
                modelItems!![position].check = 1
            else
                modelItems!![position].check = 0
        }
/*
        rowView.setOnClickListener(View.OnClickListener {
        })*/

        return rowView!!
    }

    internal class viewHolderItem{
        var name : ArrayList<String> = ArrayList()
    }

    fun setContent(list : List<FavLocationModel>){
        modelItems = list
        checkBoxState = BooleanArray(list.size)
    }

    fun getCheckedItems():ArrayList<Int>{
        var ret  = ArrayList<Int>()
        for(i in checkBoxState!!.indices){
            if(checkBoxState!![i]) ret.add(i)
        }
        return ret
    }
}