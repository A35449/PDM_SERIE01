package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.QueryRegist

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
        if(id==R.id.favorites){
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        Toast.makeText(this,"Loader Reset",1000)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI

        val cursor = CursorLoader(this, uri, null, "fav=?",arrayOf("1"), ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        val listWrapper =  ArrayList<Wrapper>()
        val counter = 0
        if (data!=null){
            while (data.moveToNext()){
                listWrapper.add(Converter.convertToForecast(data.getString(data.getColumnIndex(ForecastContract.DATA))))
                counter.inc()
            }
            if(listWrapper.size != 0 || favList == null)
                favList = listWrapper
        }
        fillList(favList!!)
    }

    companion object{
        private var editText: EditText? = null
        private var searchButton: Button?=null
        private var adapter:HomeActivityListAdapter?=null
        private var lv:ListView?=null
        private var country_code:String?=null
        private var favList : ArrayList<Wrapper>?= null
        val LOADER_ID =2
        val COUNT = 0
    }

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
            }
        }

        searchButton?.setOnClickListener({
            val synchronizer = Syncronizer(applicationContext, API_Forecast(applicationContext) )
            var location = editText?.text.toString()
            var ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location, country_code)
            var contains = file_string?.contains(ps)
            if (contains!!) {
                intent.putExtra("location",location+","+ country_code)
                synchronizer.syncronizeSearch(QueryRegist(location, country_code!!)) //marked favorite
            } else {
                Toast.makeText(this, "Location Unavailable/Incorrect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
       /** var list = favList
        if(list == null) list = ArrayList<Wrapper>()
        else{
            fillList(list)
        }*/
    }
    private fun fillList(list: List<Wrapper>) {

        if(adapter == null || (adapter != null && list!=null && adapter!!.count != list.size) ){
            adapter = HomeActivityListAdapter(this,R.id.favoriteListView,list)
        }
        //adapter?.notifyDataSetChanged()
        lv!!.adapter = adapter
    }

    private fun getNumberFavorites() : Int {
        return 0
    }
}
