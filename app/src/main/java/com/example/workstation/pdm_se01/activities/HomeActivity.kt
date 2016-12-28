package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string
import com.example.workstation.pdm_se01.model.Forecast.Wrapper

import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import android.support.v4.widget.SimpleCursorAdapter
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() ,LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_weather_by_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val myIntent = Intent(this, SettingsActivity::class.java)
            this.startActivity(myIntent)
            return true
        }
        if (id == R.id.favorites) {
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI

        val cursor = CursorLoader(this.applicationContext, uri, null, "fav=?", arrayOf("1"), ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        fillList(data!!)
    }

    companion object {
        private var editText: EditText? = null
        private var searchButton: Button? = null
        private var lv: ListView? = null
        private var country_code: String? = null
        val LOADER_ID = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loaderManager.initLoader(LOADER_ID, null, this)
        editText = findViewById(R.id.LocationInput) as EditText
        searchButton = findViewById(R.id.search_button) as Button
        lv = findViewById(R.id.favoriteListView) as ListView
        lv!!.setOnItemClickListener { adapterView, view, i, l ->
                val myIntent = Intent(this, WeatherByLocation::class.java)
                val info = view.findViewById(R.id.informationText) as TextView
                val txt = info.text
                val parsed = txt.split("\n")
                val dataRaw = parsed[0].removePrefix("Local :")
                val dd = dataRaw.split(",")

                myIntent.putExtra("location", dataRaw)
                this.startActivity(myIntent)
        }
        var searchableSpinner = findViewById(R.id.countrySpinner) as SearchableSpinner
        searchableSpinner.setTitle(getString(R.string.selectCountry))
        searchableSpinner.setPositiveButton(getString(R.string.positiveButton))
        searchableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                country_code = resources.getStringArray(R.array.countries_code)[position].toUpperCase()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        searchButton?.setOnClickListener({
            val synchronizer = Syncronizer(applicationContext, API_Forecast(applicationContext))
            var location = editText?.text.toString()
            var ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location, country_code)
            var contains = file_string?.contains(ps)
            if (contains!!) {
                intent.putExtra("location", location + "," + country_code)
                synchronizer.syncronizeSearch(QueryRegist(location, country_code!!)) //marked favorite
            } else {
                Toast.makeText(this, "Location Unavailable/Incorrect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun fillList(cursor: Cursor) {

        val from = arrayOf("data","city")
        val to = intArrayOf(R.id.weatherImage,R.id.informationText)
        val adapter = SimpleCursorAdapter(applicationContext,
                R.layout.home_activity_weather_row,
                cursor, from, to)
        val binder = MyCustomViewBinder()
        adapter.setViewBinder(binder)
        lv!!.adapter = adapter
    }


    private inner class MyCustomViewBinder : android.support.v4.widget.SimpleCursorAdapter.ViewBinder {

        var holder: WeatherHolder
        var obj : Wrapper ?= null
        var count: Int
        init{
            count = 0;
            holder = WeatherHolder()
        }
        override fun setViewValue(view: View, cursor: Cursor, columnIndex: Int): Boolean {
            if(obj == null){
                var data = cursor.getString(cursor.getColumnIndex(ForecastContract.DATA))
                obj = Converter.convertToForecast(data)
            }
            if(count == 2){
                obj = null
                count = 0
            }
            if(view.id == R.id.weatherImage){
                val vi = view as ImageView
                holder!!.imageIcon = vi;
                val myPicasso = Picasso.with(this@HomeActivity)
                myPicasso.setIndicatorsEnabled(true)
                myPicasso.load("http://openweathermap.org/img/w/" + obj!!.list[0].weather[0].icon + ".png").into(holder!!.imageIcon)
            }
            if(view.id == R.id.informationText){
                holder!!.information= view as TextView
                holder!!.city =  obj!!.city.name
                holder!!.country = obj!!.city.country
                holder!!.min = obj!!.list[0].temp.min
                holder!!.max = obj!!.list[0].temp.max
                val  allInfo="Local :"+  holder!!.city+","+holder!!.country +"\n"+
                        "Min: " + obj!!.list[0].temp.min+"ºC\n"+
                        "Max: "+ obj!!.list[0].temp.max+"ºC\n"

                holder!!.information?.text=allInfo;
            }
            return true
        }
    }

    internal class WeatherHolder {
        var imageIcon: ImageView? = null
        var information :TextView?=null
        var city : String ? = null
        var country : String ? = null
        var min : Double ?= null
        var max : Double ?= null
    }

}

