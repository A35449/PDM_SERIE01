package com.example.workstation.pdm_se01

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

/**
 * Created by workstation on 03/11/2016.
 */

class ConnectivityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connectivity)
        refreshBtn = findViewById(R.id.refresh) as ImageButton
        refreshBtn!!.setOnClickListener {
            try {
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        messageBox = findViewById(R.id.messageBox) as TextView
        messageBox!!.text = "Sem ligação à rede."
    }

    companion object {
        private var messageBox: TextView? = null
        private var refreshBtn: ImageButton? = null
    }
}