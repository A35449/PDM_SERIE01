package com.example.workstation.pdm_se01.components.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import android.os.BatteryManager
import android.os.SystemClock
import com.example.workstation.pdm_se01.AWApplication
import com.example.workstation.pdm_se01.alarmIntent
import com.example.workstation.pdm_se01.alarmMgr
import android.content.Intent



/**
 * Created by Jos on 13/12/2016.
 */

class AWAReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent!!.action) {
            Intent.ACTION_BATTERY_CHANGED -> {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPct = level / scale.toFloat()
                Toast.makeText(context, "Battery :  " +batteryPct, Toast.LENGTH_SHORT).show()
                val shared=context?.getSharedPreferences("alarmSet",MODE_PRIVATE)
                val alarmSet = shared?.getBoolean("alarmSet",true) as Boolean

                if(batteryPct<0.5 && alarmSet){
                    val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val cancelPIntent : PendingIntent
                    val intentCancel = Intent(context, AWAReceiver::class.java)
                    cancelPIntent = PendingIntent.getBroadcast(context,0,intentCancel,0)
                    alarmManager.cancel(cancelPIntent)
                    Toast.makeText(context, "Battery lower then settings allow ,alarm unset", Toast.LENGTH_SHORT).show()

                    val shared= context?.getSharedPreferences("alarmSet", Context.MODE_PRIVATE)
                    val editor= shared?.edit()
                    editor?.putBoolean("alarmSet",false)
                    editor?.commit()

                }
                else if (batteryPct>=0.5 && !alarmSet){
                    alarmMgr?.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(),
                            6000, alarmIntent)

                    val shared= context?.getSharedPreferences("alarmSet", Context.MODE_PRIVATE)
                    val editor= shared?.edit()
                    editor?.putBoolean("alarmSet",true)
                    editor?.commit()
                    Toast.makeText(context, "Battery higher then settings allow ,alarm Set", Toast.LENGTH_SHORT).show()

                }
                else  Toast.makeText(context, "No changes done to alarm", Toast.LENGTH_SHORT).show()

            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show()
            }
            else -> {
                context?.startService(Intent(context, UpdateService::class.java))
            }
        }

    }
}
