package com.example.workstation.pdm_se01.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.workstation.pdm_se01.PreferencesActivity
import com.example.workstation.pdm_se01.R

class HomeActivity : AppCompatActivity() {
    private var favButton: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        favButton=findViewById(R.id.favButton) as ImageButton

        favButton?.setOnClickListener({
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
        })




    }


}
