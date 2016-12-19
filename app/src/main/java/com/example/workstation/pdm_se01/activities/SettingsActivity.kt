package com.example.workstation.pdm_se01.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.widget.*

import com.example.workstation.pdm_se01.R


class SettingsActivity : AppCompatActivity() {
    private var sharedPrefLocation: SharedPreferences? = null
    private var applyButton: Button? = null
    private var aboutUsButton: Button? = null
    private var seekbar1: SeekBar? = null
    private var periocityButton: Button? = null
    private var tSwitch: Switch? = null
    private var seekBarValue: TextView? = null

    private var wifisetting: Boolean? = false
    private var periocity: Long? = 100
    private var progressshow: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharedPrefLocation = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        applyButton = findViewById(R.id.applychanges) as Button
        aboutUsButton=findViewById(R.id.aboutusbutton)as Button
        seekbar1 = findViewById(R.id.seekBar) as SeekBar
        tSwitch = findViewById(R.id.switch1) as Switch
        periocityButton = findViewById(R.id.periocity) as Button
        seekBarValue = findViewById(R.id.seekBarValue) as TextView


        loadSharedpreferences()

        tSwitch!!.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked) {
                wifisetting = true
            } else
                wifisetting = false
        }
        seekbar1!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressshow = progress
                seekBarValue?.text = progress.toString() + "%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

                seekBarValue?.text = progressshow.toString() + "%"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                seekBarValue?.text = progressshow.toString() + "%"
            }
        })




        periocityButton!!.setOnClickListener({
            val addAlert = AlertDialog.Builder(this@SettingsActivity)
            addAlert.setTitle("Enter Periocity")
            addAlert.setMessage("Current Periocity: " + periocity + "min ")

            val input = EditText(this@SettingsActivity)


            input.inputType = InputType.TYPE_CLASS_NUMBER
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = lp
            addAlert.setView(input)


            addAlert.setPositiveButton("Add",
                    { dialog, x ->
                        periocity = input.text.toString().toLong()

                    })

            addAlert.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

            val addDialog = addAlert.create()
            addDialog.show()
        })

        applyButton!!.setOnClickListener({
            saveSharedpreferences()
            Toast.makeText(this@SettingsActivity, "Settings Saved", Toast.LENGTH_SHORT).show()

        })

        aboutUsButton?.setOnClickListener({
            val myIntent = Intent(this, AboutActivity::class.java)
            this.startActivity(myIntent)
        })




    }
    fun loadSharedpreferences(){
       wifisetting=sharedPrefLocation?.getBoolean("wifiDef",false)
        periocity=sharedPrefLocation?.getLong("periodicity",120)
        progressshow=sharedPrefLocation?.getInt("batteryLimit",20)
        tSwitch?.isChecked= wifisetting!!
        seekbar1?.progress = progressshow!!
        seekBarValue?.text=progressshow.toString()
        return
    }

    fun saveSharedpreferences() {

        val editor = sharedPrefLocation!!.edit()
        editor.putBoolean("wifiDef", wifisetting!!)
        editor.putLong("periodicity", periocity!!)
        editor.putInt("batteryLimit", progressshow!!)
        editor.commit()
        return
    }




}