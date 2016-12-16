package com.example.workstation.pdm_se01.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.os.Build
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*

import com.android.volley.Response
import com.example.workstation.pdm_se01.network.api.API
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.adapter.ForecastAdapter

import org.apache.commons.io.IOUtils

import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        var file_string: String? = null
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeInputData()
        val myIntent = Intent(this, HomeActivity::class.java)
                this.startActivity(myIntent)
    }

    fun initializeInputData(){
        if(file_string == null){
            val it = resources.openRawResource(R.raw.citylist)
            try {
                file_string = IOUtils.toString(it,"utf-8")
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
        return;
    }
}
