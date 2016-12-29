package com.example.workstation.pdm_se01.activities

import android.content.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.QueryRegist

import org.apache.commons.io.IOUtils

import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        var file_string: String? = null
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeInputData()
        val myIntent = Intent(this, HomeActivity::class.java)
                this.startActivity(myIntent)
    }

    fun initializeInputData(){
        if(file_string == null){
            val it = resources.openRawResource(R.raw.citylist)
            try {
                file_string = IOUtils.toString(it,"utf-8")
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
        return;
    }
}
