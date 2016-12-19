package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.example.workstation.pdm_se01.R
import java.util.ArrayList
import com.example.workstation.pdm_se01.adapter.FavLocationListAdapter
import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel

import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.workstation.pdm_se01.model.Forecast.Wrapper
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist


class PreferencesActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        val updatedModel = ArrayList<FavLocationModel>()
        val counter = 0
        var elem : FavLocationModel
        if (data != null) {
            while (data.moveToNext()) {
                elem = FavLocationModel()
                elem.location = data.getString(data.getColumnIndex(ForecastContract.CITY)) + "," + data.getString(data.getColumnIndex(ForecastContract.COUNTRY))
                updatedModel.add(elem)
            }
            if(updatedModel.size != 0 || listModel == null){
                listModel = updatedModel
            }
        }
        fillList(listModel as ArrayList<FavLocationModel>)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI
        val cursor = CursorLoader(this, uri, null, "fav=?", arrayOf("1"), ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }

    companion object {
        private var listModel: ArrayList<FavLocationModel>? = null
        private var addPref: Button? = null
        private var removePref: Button? = null
        private var lv: ListView? = null
        private var adapter: FavLocationListAdapter? = null
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
        if (id == R.id.favorites) {
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        loaderManager.initLoader(10,null,this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //   sharedPrefLocation = getSharedPreferences("Location", Context.MODE_PRIVATE)
        lv = findViewById(R.id.favList) as ListView
        addPref = findViewById(R.id.addLocationButton) as Button
        removePref = findViewById(R.id.removeLocationButton) as Button

        //if (!favList.isEmpty())

        addPref!!.setOnClickListener({
            val addAlert = AlertDialog.Builder(this@PreferencesActivity)
            addAlert.setTitle("Add Location")
            addAlert.setMessage("Enter Location")

            val input = EditText(this@PreferencesActivity)
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
            input.hint = "ex:London,GB"
            addAlert.setView(input)

            addAlert.setPositiveButton("Add",
                    { dialog, x ->
                        val rawLocation = input.text.toString()
                        val location = rawLocation.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

                        if (location.size < 2) {
                            Toast.makeText(this@PreferencesActivity, "Location Unavailable", Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }
                        val ps = String.format("\"name\":\"%s\",\"country\":\"%s\"", location[0], location[1].toUpperCase())
                        if (MainActivity.file_string!!.contains(ps)) {

                            val synchronizer = Syncronizer(applicationContext, API_Forecast(applicationContext))
                            synchronizer.syncronizeSingle(QueryRegist(location[0], location[1], 1)) //marked favorite

                            val newEntry = FavLocationModel()
                            newEntry.location = rawLocation
                            listModel?.add(newEntry)

                            adapter = null
                            fillList(listModel!!)
                            //finish()
                            //startActivity(intent)

                            Toast.makeText(this@PreferencesActivity, "Preference Saved", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this@PreferencesActivity, "Location Unavailable", Toast.LENGTH_SHORT).show()
                        }
                    })

            addAlert.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

            val addDialog = addAlert.create()
            addDialog.show()
        })

        removePref!!.setOnClickListener(View.OnClickListener { v->

            val synchronizer = Syncronizer(applicationContext, API_Forecast(applicationContext))
            //val txt = v.findViewById(R.id.textView1) as TextView
            val checkedList = adapter!!.getCheckedItems()
            for (i: Int in checkedList) {
                val l = listModel!!
                val location = l.get(i).location
                var parsed = location?.split(",")
                synchronizer.syncronizeSingle(QueryRegist(parsed!![0], parsed[1],0))
                listModel!!.removeAt(i)
            }

            //this.applicationContext.contentResolver.notifyChange(ForecastContract.CONTENT_URI, null)

/*            favList
                    .filter { it.check==1 }
                    .forEach {
                        var parsed= it.location.split(",")
                        synchronizer.syncronizeSingle(QueryRegist(parsed[0],parsed[1],0))
                        favList.remove(it)
                    }*/
/*
            saveSharedpreferences(favList)


*/          //Toast.makeText(this@PreferencesActivity, "Preference Saved", Toast.LENGTH_SHORT).show()
            adapter = null
            fillList(listModel!!)
           /* adapter = FavLocationListAdapter(this,R.id.favList,
                    listModel!!)
            lv!!.adapter = adapter*/
        })


        /*private fun saveSharedpreferences(prefsList: List<FavLocationModel>) {

        var locations = ""
        for (i in prefsList.indices) {
            var location = prefsList.get(i).location
            locations += location + "/"
            var parsed =location.split(",")
        }

        val editor = sharedPrefLocation!!.edit()
        editor.clear()
        editor.putString("locals", locations)
        editor.commit()
    }*/

    }
        private fun fillList(list: ArrayList<FavLocationModel>) {
            if (adapter == null) {
                adapter = FavLocationListAdapter(this, R.id.favList,
                        list)
            }
            lv!!.adapter = adapter
        }
    }


