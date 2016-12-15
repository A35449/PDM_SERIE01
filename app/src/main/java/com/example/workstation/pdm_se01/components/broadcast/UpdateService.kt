package com.example.workstation.pdm_se01.components.broadcast

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import android.os.Binder
import android.util.Log
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.WeatherActivity


/**
 * Created by Jos on 14/12/2016.
 */
class UpdateService : IntentService("UpdateService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d("ttt","ttt")
    }

}