package com.example.workstation.pdm_se01

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.View
import android.widget.*
import com.example.workstation.pdm_se01.activities.MainActivity

import org.codehaus.jackson.map.ser.std.ObjectArraySerializer
import org.codehaus.jackson.map.ser.std.StringSerializer

import java.io.IOException
import java.io.Serializable
import java.util.ArrayList
import java.util.HashSet

import com.example.workstation.pdm_se01.activities.MainActivity.Companion
import com.example.workstation.pdm_se01.activities.MainActivity.Companion.file_string
import com.example.workstation.pdm_se01.adapter.FavLocationListAdapter
import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel

class PreferencesActivity : AppCompatActivity() {

    private var addPref: Button? = null
    private var removePref: Button? = null
    private var lv: ListView? = null
    private var sharedPrefLocation: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharedPrefLocation = getSharedPreferences("Location", Context.MODE_PRIVATE)
        lv = findViewById(R.id.favList) as ListView
        addPref = findViewById(R.id.addLocationButton) as Button
        removePref = findViewById(R.id.removeLocationButton) as Button

        val favList = sharedPrefs()

        if (!favList.isEmpty())
            fillList(favList)


        addPref!!.setOnClickListener({
            val addAlert = AlertDialog.Builder(this@PreferencesActivity)
            addAlert.setTitle("Add Location")
            addAlert.setMessage("Enter Location")

            val input = EditText(this@PreferencesActivity)
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
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
                            //validar localizaçao intruduzida
                            var favLocationModel:FavLocationModel?=null
                            favLocationModel?.location=rawLocation
                            favLocationModel?.check=0

                                favList.add(favLocationModel!!)

                            saveSharedpreferences(favList)
                            fillList(favList)

                            Toast.makeText(this@PreferencesActivity, "Preference Saved", Toast.LENGTH_SHORT).show()


                        } else {
                            Toast.makeText(this@PreferencesActivity, "Location Unavailable", Toast.LENGTH_SHORT).show()
                        }
                    })

            addAlert.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

            val addDialog = addAlert.create()
            addDialog.show()
        })





        removePref!!.setOnClickListener(View.OnClickListener {
          /*  val removeAlert = AlertDialog.Builder(this@PreferencesActivity)
            removeAlert.setTitle("Remove Location")
            removeAlert.setMessage("Enter Location")


            val input = EditText(this@PreferencesActivity)
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
            removeAlert.setView(input)



            removeAlert.setPositiveButton("Remove",
                    DialogInterface.OnClickListener { dialog, x ->
                        val rawLocation = input.text.toString()
                        val location = rawLocation.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

                        if (location.size < 2) {
                            Toast.makeText(this@PreferencesActivity, "Preference doesn't exist", Toast.LENGTH_SHORT).show()
                            return@OnClickListener
                        }
                        val ps = java.lang.String.format("\"name\":\"%s\",\"country\":\"%s\"", location[0], location[1].toUpperCase())
                        if (file_string!!.contains(ps)) {
                            //validar localizaçao intruduzida

                            for (i in favList.indices) {

                                if (rawLocation == favList[i]) {
                                    favList.removeAt(i)
                                }

                            }
                            saveSharedpreferences(favList)
                            fillList(sharedPrefs)

                            Toast.makeText(this@PreferencesActivity, "Preference Saved", Toast.LENGTH_SHORT).show()


                        } else {
                            Toast.makeText(this@PreferencesActivity, "Preference doesn't exist", Toast.LENGTH_SHORT).show()
                        }
                    })


            removeAlert.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

            val removeDialog = removeAlert.create()
            removeDialog.show()*/





        })

    }

    private fun sharedPrefs(): MutableList<FavLocationModel> {

        val preferences = ArrayList<FavLocationModel>()
        val rawlocations = sharedPrefLocation!!.getString("locals", null) ?: return preferences
        val locations = rawlocations.split("/".toRegex()).dropLastWhile({ it.isEmpty() })//.toTypedArray()

        for (i in locations.indices) {
            var favLocationModel=FavLocationModel()
            favLocationModel.check=0;
            favLocationModel.location=locations[i];
            preferences.add(favLocationModel)
        }
        return preferences
        }


    private fun saveSharedpreferences(prefsList: List<FavLocationModel>) {

        var locations = ""
        for (i in prefsList.indices) {
            locations += prefsList.get(i).location + "/"

        }


        val editor = sharedPrefLocation!!.edit()
        editor.clear()
        editor.putString("locals", locations)
        editor.commit()

    }


    private fun fillList(list: List<FavLocationModel>) {
        val adapter = FavLocationListAdapter(this,R.id.favList,
                 list)
        lv!!.adapter = adapter
    }

}

