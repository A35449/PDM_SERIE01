package com.example.workstation.pdm_se01.activities

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.workstation.pdm_se01.network.API
//import com.example.workstation.pdm_se01.AWA.AWA
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.provider.AWAContract
import com.example.workstation.pdm_se01.provider.DbSchema
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.adapter.ForecastAdapter
import com.example.workstation.pdm_se01.utils.Utils
import com.example.workstation.pdm_se01.activities.AboutActivity

import org.apache.commons.io.IOUtils

import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {


    companion object {
        private var adapter: ArrayAdapter<Forecast>? = null
        private val LIST_INSTANCE_STATE = "ListViewState"
        private var file_string: String? = null

        private var lv: ListView? = null

        private var state: Parcelable? = null
        private var aboutUs: Button? = null

        private var api: API? = null

        private var txtMain: TextView? = null
        private var editText: EditText? = null
        private var getWeather: Button? = null
        private var jsonRequest: String? = null
        private var bundle: Bundle? = null
        private var shared: SharedPreferences? = null
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public override fun onResume() {
        super.onResume()
        try {
            if (!Utils.checkConnectivity(applicationContext)) throw Exception()
        } catch (e: Exception) {
            launchConnectivityErrWindow()
        }
    }


    private fun launchConnectivityErrWindow() {
        val intent = Intent(this, ConnectivityActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val dc = AWA(this,contentResolver)

        dc.sendNotification("Awa","Tempo para hoje", "")

        val cc = contentResolver;
        val uri = AWAContract.Weather.getWeatherList()

//        val cv = ContentValues()
//        cv.put(DbSchema.Weather.COL_DATA,"UKLondonData")
//        cv.put(DbSchema.Weather.COL_COUNTRY,"UK")
//        cv.put(DbSchema.Weather.COL_CITY,"London")
//        cc.insert(uri,cv)
        */
        val end = ""
        //bundle = savedInstanceState;
        shared =  getSharedPreferences("yawaPref", MODE_PRIVATE)
        lv = findViewById(R.id.day_forecast_list) as ListView
        editText = findViewById(R.id.LocationInput) as EditText
        getWeather = findViewById(R.id.GetWeather) as Button
        aboutUs = findViewById(R.id.aboutus) as Button

        if(adapter != null){
            lv?.setAdapter(adapter)
        }else {
            var savedRequest = getSharedPreferences("yawaPref", MODE_PRIVATE).getString("req", null)
            if(savedRequest != null){
                try {
                    var wrap = Converter.convertToForecast(savedRequest)
                    fillList(wrap.getList())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        api = API(this.applicationContext)

        var repHandler = Response.Listener<String>(){
            fun onResponse(response : String){
                var editor = shared?.edit()
                editor?.putString("req",response)
                editor?.commit()
                try{
                    var wrap = Converter.convertToForecast(response)
                    fillList(wrap.getList())
                }catch(ex: IOException){
                    System.out.print(ex!!.message)
                }
            }
        };

        val errHandler = Response.ErrorListener(){
            fun onErrorResponse(error : VolleyError){
                System.out.println(error.message)
            }
        }

        getWeather?.setOnClickListener({

                var location = editText?.getText().toString().split(",")
                if (location.size < 2){
                    Toast.makeText(this,"Location Unavailable", Toast.LENGTH_SHORT).show()
                }
                else {
                    var ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location[0], location[1].toUpperCase())
                    var contains = file_string?.contains(ps)
                    if (contains!!) {
                        api?.getForecast(location[0], repHandler, errHandler)
                        //
                    } else {
                        Toast.makeText(this, "Location Unavailable", Toast.LENGTH_SHORT).show()
                    }
                }
        });


        aboutUs?.setOnClickListener({
                val myIntent = Intent(this, AboutActivity::class.java)
                this.startActivity(myIntent)
        })

        initializeInputData()
    }


      public fun initializeInputData(){
        if(file_string ==null){
            val it = getResources().openRawResource(R.raw.citylist)
            try {
                file_string = IOUtils.toString(it,"utf-8")
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
        return;
    }

    private fun fillList(list: List<Forecast> ) {
        adapter = ForecastAdapter(this, R.layout.forecast_item, list)
        lv!!.setAdapter(adapter)
    }
}
