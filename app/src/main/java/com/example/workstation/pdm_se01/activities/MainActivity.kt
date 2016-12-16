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
import com.example.workstation.pdm_se01.network.API
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.adapter.ForecastAdapter

import org.apache.commons.io.IOUtils

import java.io.IOException

class MainActivity : AppCompatActivity() {


    companion object {
        private var adapter: ArrayAdapter<Forecast>? = null
        private val LIST_INSTANCE_STATE = "ListViewState"
        var file_string: String? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myIntent = Intent(this, HomeActivity::class.java)
                this.startActivity(myIntent)






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
                    fillList(wrap.list)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        api = API(this.applicationContext)

        var repHandler = Response.Listener<String>(){response ->
                var editor = shared?.edit()
                editor?.putString("req",response)
                editor?.commit()
                try{
                    var wrap = Converter.convertToForecast(response)
                    fillList(wrap.list)
                }catch(ex: IOException){
                    System.out.print(ex.message)
                }
        }

        val errHandler = Response.ErrorListener(){error->
                Toast.makeText(this, error.message,Toast.LENGTH_LONG).show()
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


      fun initializeInputData(){
        if(file_string ==null){
            val it = resources.openRawResource(R.raw.citylist)
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
        lv!!.adapter = adapter
    }
}
