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
import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel

import android.support.v4.widget.SimpleCursorAdapter
import android.view.Menu
import android.view.MenuItem
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.QueryRegist


class PreferencesActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        fillListCursor(data!!)
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
        private var adapter: SimpleCursorAdapter? = null
        internal var checkBoxState : ArrayList<Int> ?= null
    }

    init{
        checkBoxState = ArrayList<Int>()
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

        lv = findViewById(R.id.favList) as ListView
        addPref = findViewById(R.id.addLocationButton) as Button
        removePref = findViewById(R.id.removeLocationButton) as Button

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

            for(i in checkBoxState!!.iterator()){
                val view = adapter!!.getView(i,null,null)
                    val location = (view.findViewById(R.id.textView1) as TextView).text
                    var parsed = location?.split(",")
                    val regist = QueryRegist(parsed!![0], parsed!![1], 0)
                    val nDelete = synchronizer.removeFav(regist)
            }
        })
    }

    private fun fillListCursor(cursor:Cursor) {

        val from = arrayOf("data","city")
        val to = intArrayOf(R.id.textView1,R.id.checkBox1)
        adapter = SimpleCursorAdapter(applicationContext,
                R.layout.location_list_row,
                cursor, from, to)
        adapter!!.viewBinder = FavoriteViewBinder()
        lv!!.adapter = adapter
    }

    private inner class FavoriteViewBinder : android.support.v4.widget.SimpleCursorAdapter.ViewBinder {

        override fun setViewValue(view: View, cursor: Cursor, columnIndex: Int): Boolean {

            if(view.id == R.id.textView1){
                val vi = view as TextView
                val txt = cursor.getString(cursor.getColumnIndex(ForecastContract.CITY)) + "," + cursor.getString(cursor.getColumnIndex(ForecastContract.COUNTRY))
                vi.text = txt
            }

            if(view.id == R.id.checkBox1){
                val cb = view as CheckBox
                cb.setOnClickListener { v ->
                    val chk = v as CheckBox
                    val isChecked = chk.isChecked
                    if(isChecked){
                        checkBoxState!!.add(cursor.position)
                    }
                    else{
                        checkBoxState!!.remove(cursor.position)
                    }
                }
            }
            return true
        }
    }

}


