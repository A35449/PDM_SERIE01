package com.example.workstation.pdm_se01.components.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by Jos on 13/12/2016.
 */

class AWAReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Battery low.", Toast.LENGTH_LONG).show();
    }

}
