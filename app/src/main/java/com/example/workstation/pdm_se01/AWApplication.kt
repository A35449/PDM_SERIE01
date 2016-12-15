package com.example.workstation.pdm_se01

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.example.workstation.pdm_se01.activities.MainActivity
import com.example.workstation.pdm_se01.components.broadcast.AWAReceiver

/**
 * Created by Jos on 14/12/2016.
 */

private var alarmMgr: AlarmManager? = null
private var alarmIntent: PendingIntent? = null

class AWApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        initAlarm()
    }
    fun initAlarm(){
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AWAReceiver::class.java)
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent,0)
        alarmMgr!!.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                60000, alarmIntent)
    }
}