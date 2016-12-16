package com.example.workstation.pdm_se01.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.workstation.pdm_se01.activities.PreferencesActivity
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string

class HomeActivity : AppCompatActivity() {
    private var favButton: ImageButton? = null
    private var editText: EditText? = null
    private var searchButton: Button?=null
    private var settingsButton:ImageButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        favButton=findViewById(R.id.favButton) as ImageButton

        editText = findViewById(R.id.LocationInput) as EditText
        searchButton=findViewById(R.id.search_button)as Button
        settingsButton=findViewById(R.id.settingsButton)as ImageButton

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
        });
        settingsButton?.setOnClickListener({
            val myIntent = Intent(this, SettingsActivity::class.java)
            this.startActivity(myIntent)
        })
    }
}
