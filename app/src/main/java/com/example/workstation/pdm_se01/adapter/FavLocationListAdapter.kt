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

/**
 * Created by Tiago on 15/12/2016.
 */

class FavLocationListAdapter(context: Context, resId: Int, resource: List<FavLocationModel>) : ArrayAdapter<FavLocationModel>(context, resId, resource) {

    internal var modelItems: List<FavLocationModel>
    internal var checkBoxState : BooleanArray

    init {
        this.modelItems = resource
        checkBoxState = BooleanArray(resource.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        var viewHolder:viewHolderItem
        // TODO Auto-generated method stub
        if (rowView==null)
        {
            val inflater = (context as Activity).layoutInflater
            rowView = inflater.inflate(R.layout.location_list_row, parent, false)
            viewHolder= viewHolderItem()

            val name = rowView.findViewById(R.id.textView1) as TextView
            val cb = rowView.findViewById(R.id.checkBox1) as CheckBox
            cb.setOnClickListener { v ->

               val chk = v as CheckBox
               checkBoxState[position] = chk.isChecked

                modelItems!![position].check = 1 xor modelItems!![position].check

                if (modelItems!![position].check == 1){
                    cb.isChecked = true
                }
                else{
                    cb.isChecked = false
                }

            }
            name.text = modelItems!![position].location
            val myIntent = Intent(context, WeatherByLocation::class.java)

            rowView.setOnClickListener(View.OnClickListener {
                val sync = Syncronizer(context.applicationContext,API_Forecast(context.applicationContext))
                val parsedlocation = modelItems!![position].location.split(",")
                val query = QueryRegist(parsedlocation[0],parsedlocation[1])
                sync.syncronizeSearch(query)
            })

            viewHolder.textbox = name
            viewHolder.checkbox =cb

            rowView.setTag(viewHolder)
        }else{
            viewHolder = rowView.getTag() as viewHolderItem
        }
        viewHolder.checkbox?.setChecked(checkBoxState[position])

        return rowView!!
    }

    internal class viewHolderItem{
        var textbox:TextView?=null
        var checkbox:CheckBox ?=null
    }

    fun setContent(list : List<FavLocationModel>){
        modelItems = list
        checkBoxState = BooleanArray(list.size)
    }
}