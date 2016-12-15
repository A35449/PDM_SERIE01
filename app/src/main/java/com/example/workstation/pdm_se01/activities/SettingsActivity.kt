package com.example.workstation.pdm_se01.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar

import com.example.workstation.pdm_se01.R

class SettingsActivity : AppCompatActivity() {
    private var sharedPrefLocation: SharedPreferences? = null
    private var applyButton:Button?=null
    private var aboutUsButton:Button?=null
    private var seekbar:SeekBar?=null
    private var editText:EditText?=null

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


















    }

}
