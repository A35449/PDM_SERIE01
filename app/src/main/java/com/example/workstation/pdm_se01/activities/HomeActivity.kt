package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.workstation.pdm_se01.activities.PreferencesActivity
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string
import com.example.workstation.pdm_se01.adapter.HomeActivityListAdapter
import com.example.workstation.pdm_se01.model.Forecast.Wrapper

import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.Converter
import java.util.*

class HomeActivity : AppCompatActivity() ,LoaderManager.LoaderCallbacks<Cursor>{

   companion object{
       val LOADER_ID =2
   }





    override fun onLoaderReset(loader: Loader<Cursor>?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI


        val cursor = CursorLoader(this, uri, null, ForecastContract.FAV + " = ?",arrayOf("1"), ForecastContract.DEFAULT_SORT_ORDER)
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
    //private var sharedPrefLocation: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        favButton=findViewById(R.id.favButton) as ImageButton
        editText = findViewById(R.id.LocationInput) as EditText
        searchButton=findViewById(R.id.search_button)as Button
        settingsButton=findViewById(R.id.settingsButton)as ImageButton
        lv=findViewById(R.id.favoriteListView)as ListView


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





    private fun fillList(list: List<Wrapper>) {
        adapter = HomeActivityListAdapter(this,R.id.favoriteListView,
                list)
        lv!!.adapter = adapter
    }

}
