package com.example.workstation.pdm_se01.components.broadcast

import android.app.*
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.utils.Utils
import java.util.*


/**
 * Created by Jos on 14/12/2016.
 */
class UpdateService : IntentService("UpdateService") {

        override fun onHandleIntent(intent: Intent?) {
            Utils.sendNotification(applicationContext,"Update Started","Update Started","")
            syncNow()
        }

        fun syncNow(){
            val sync = Syncronizer(applicationContext,API_Forecast(applicationContext))
            sync.snycronize(sync.getFavorites())
            Toast.makeText(this,"Update Record",1).show()
        }
    }