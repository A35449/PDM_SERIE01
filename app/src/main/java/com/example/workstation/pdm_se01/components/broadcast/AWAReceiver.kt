package com.example.workstation.pdm_se01.components.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
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
            Intent.ACTION_BATTERY_LOW-> {
                Toast.makeText(context, "Battery LOW", Toast.LENGTH_LONG).show()
                val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val cancelPIntent : PendingIntent
                val intentCancel = Intent(context, AWAReceiver::class.java)
                cancelPIntent = PendingIntent.getBroadcast(context,0,intentCancel,0)
                alarmManager.cancel(cancelPIntent)
            }
            Intent.ACTION_BATTERY_OKAY -> {
                Toast.makeText(context, "Battery OKAY", Toast.LENGTH_LONG).show()
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Toast.makeText(context, "Boot Completed", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(context, "Alarm!!!", Toast.LENGTH_LONG).show()
            }
        }

    }
}
