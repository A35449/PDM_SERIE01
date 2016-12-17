package com.example.workstation.pdm_se01.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.workstation.pdm_se01.activities.PreferencesActivity
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string
import com.example.workstation.pdm_se01.adapter.HomeActivityListAdapter
import com.example.workstation.pdm_se01.model.HomeActivityWeather.HomeActivityWeatherModel
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var favButton: ImageButton? = null
    private var editText: EditText? = null
    private var searchButton: Button?=null
    private var settingsButton:ImageButton?=null
    private var adapter:HomeActivityListAdapter?=null
    private var lv:ListView?=null
    private var sharedPrefLocation: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        favButton=findViewById(R.id.favButton) as ImageButton
        editText = findViewById(R.id.LocationInput) as EditText
        searchButton=findViewById(R.id.search_button)as Button
        settingsButton=findViewById(R.id.settingsButton)as ImageButton
        lv=findViewById(R.id.favoriteListView)as ListView

        sharedPrefLocation = getSharedPreferences("Location", Context.MODE_PRIVATE)

        val favList = loadSharedPrefs()

        if (!favList.isEmpty())
            fillList(favList)








        favButton?.setOnClickListener({
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
        })
        searchButton?.setOnClickListener({
          var extrainfo =editText?.getText().toString()
            var location = editText?.getText().toString().split(",")
            if (location.size < 2){
                Toast.makeText(this,"Location Unavailable", Toast.LENGTH_SHORT).show()
            }
            else {
                var ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location[0], location[1].toUpperCase())
                var contains = file_string?.contains(ps)
                if (contains!!) {
                    val myIntent = Intent(this, WeatherByLocation::class.java)
                    myIntent.putExtra("location",extrainfo)
                    this.startActivity(myIntent)
                    //
                } else {
                    Toast.makeText(this, "Location Unavailable", Toast.LENGTH_SHORT).show()
                }
            }
        })
        settingsButton?.setOnClickListener({
            val myIntent = Intent(this, SettingsActivity::class.java)
            this.startActivity(myIntent)
        })



















    }

        private fun loadSharedPrefs(): MutableList<HomeActivityWeatherModel> {

            val preferences = ArrayList<HomeActivityWeatherModel>()
            val rawlocations = sharedPrefLocation!!.getString("locals", null) ?: return preferences
            val locations = rawlocations.split("/".toRegex()).dropLastWhile({ it.isEmpty() })//.toTypedArray()

            for (i in locations.indices) {
                var favLocationModel=HomeActivityWeatherModel()

                favLocationModel.location=locations[i];

                //pedido de obtençao de forecasts


                preferences.add(favLocationModel)
            }

            return preferences
        }
















    private fun fillList(list: List<HomeActivityWeatherModel>) {
        adapter = HomeActivityListAdapter(this,R.id.favoriteListView,
                list)
        lv!!.adapter = adapter
    }

}
