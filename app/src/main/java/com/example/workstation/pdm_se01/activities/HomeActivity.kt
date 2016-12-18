package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.workstation.pdm_se01.activities.PreferencesActivity
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string
import com.example.workstation.pdm_se01.adapter.HomeActivityListAdapter
import com.example.workstation.pdm_se01.model.Forecast.Wrapper

import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.Converter
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView



class HomeActivity : AppCompatActivity() ,LoaderManager.LoaderCallbacks<Cursor>{

   companion object{
       val LOADER_ID =2
   }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_weather_by_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            val myIntent = Intent(this, SettingsActivity::class.java)
            this.startActivity(myIntent)
            return true
        }
        if(id==R.id.favorites){
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }




    override fun onLoaderReset(loader: Loader<Cursor>?) {
        val d = 1//throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI


        val cursor = CursorLoader(this, uri, null, "fav=?",arrayOf("1"), ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }
    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
       val listWrapper =  ArrayList<Wrapper>()


        if (data!=null){
            while (data.moveToNext()){

                listWrapper.add(Converter.convertToForecast(data.getString(data.getColumnIndex(ForecastContract.DATA))))


            }
         fillList(listWrapper)


        }

    }


    private var favButton: ImageButton? = null
    private var editText: EditText? = null
    private var searchButton: Button?=null
    private var settingsButton:ImageButton?=null
    private var adapter:HomeActivityListAdapter?=null
    private var lv:ListView?=null
    private var country_code:String?=null
    //private var sharedPrefLocation: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
            loaderManager.initLoader(LOADER_ID,null,this)

        editText = findViewById(R.id.LocationInput) as EditText
        searchButton=findViewById(R.id.search_button)as Button
        lv=findViewById(R.id.favoriteListView)as ListView

        var searchableSpinner = findViewById(R.id.countrySpinner) as SearchableSpinner
        searchableSpinner.setTitle(getString(R.string.selectCountry))
        searchableSpinner.setPositiveButton(getString(R.string.positiveButton))
        searchableSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                country_code = resources.getStringArray(R.array.countries_code)[position].toUpperCase()
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }

        searchButton?.setOnClickListener({
            var location = editText?.text.toString()
            var ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location, country_code)
            var contains = file_string?.contains(ps)
            if (contains!!) {
                val myIntent = Intent(this, WeatherByLocation::class.java)
                myIntent.putExtra("location",location+","+country_code)
                this.startActivity(myIntent)
            } else {
                Toast.makeText(this, "Location Unavailable/Incorrect", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun fillList(list: List<Wrapper>) {
        adapter = HomeActivityListAdapter(this,R.id.favoriteListView,
                list)
        lv!!.adapter = adapter
    }

}
