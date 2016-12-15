package com.example.workstation.pdm_se01

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.*
import android.os.SystemClock
import com.example.workstation.pdm_se01.activities.MainActivity
import com.example.workstation.pdm_se01.components.broadcast.AWAReceiver
import android.content.Intent.ACTION_BATTERY_CHANGED


/**
 * Created by Jos on 14/12/2016.
 */


var alarmMgr: AlarmManager? = null
var alarmIntent: PendingIntent? = null

class AWApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        setAlarm()
        setBatteryIntentFilter()
    }

    fun setAlarm(){
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AWAReceiver::class.java)
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent,0)
        alarmMgr!!.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                6000, alarmIntent)

        val shared=this.getSharedPreferences("alarmSet", Context.MODE_PRIVATE)
        val editor=shared.edit()
        editor.putBoolean("alarmSet",true)
        editor?.commit()


    }

    private fun setBatteryIntentFilter() {
        val batteryLevelFilter = IntentFilter(ACTION_BATTERY_CHANGED)
        registerReceiver(AWAReceiver(), batteryLevelFilter)
    }
}