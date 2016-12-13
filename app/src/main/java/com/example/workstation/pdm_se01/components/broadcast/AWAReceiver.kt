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
        when (intent!!.action) {
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, "Battery LOW", Toast.LENGTH_LONG).show()
            Intent.ACTION_BATTERY_OKAY -> Toast.makeText(context, "Battery OKAY", Toast.LENGTH_LONG).show()
            Intent.ACTION_BOOT_COMPLETED -> Toast.makeText(context, "Boot Completed", Toast.LENGTH_LONG).show()

        }
    }
}
