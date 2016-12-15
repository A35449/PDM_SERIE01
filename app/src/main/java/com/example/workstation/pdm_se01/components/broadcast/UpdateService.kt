package com.example.workstation.pdm_se01.components.broadcast

import android.app.*
import android.content.Intent
import com.example.workstation.pdm_se01.utils.Utils


/**
 * Created by Jos on 14/12/2016.
 */
class UpdateService : IntentService("UpdateService") {

        override fun onHandleIntent(intent: Intent?) {
            Utils.sendNotification(applicationContext,"Update Started","Update Started","")
        }

    }