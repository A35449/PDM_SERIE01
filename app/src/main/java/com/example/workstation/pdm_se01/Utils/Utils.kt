package com.example.workstation.pdm_se01.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.activities.HomeActivity
import com.example.workstation.pdm_se01.activities.MainActivity
import com.example.workstation.pdm_se01.components.notification.NotificationReceiver

/**
 * Created by workstation on 27/10/2016.
 */

class Utils {
    companion object {
        fun sendNotification(context: Context ,title: String, description: String, icon: String) {
            val mBuilder = NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(description)
            val resultIntent = Intent(context, NotificationReceiver::class.java)
            val stackBuilder = TaskStackBuilder.create(context)
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(HomeActivity::class.java)

            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )

            mBuilder.setContentIntent(resultPendingIntent)

            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mId allows you to update the notification later on.
            mNotificationManager.notify(1, mBuilder.build())
        }
    }
}
