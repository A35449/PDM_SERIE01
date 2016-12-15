package com.example.workstation.pdm_se01.network

import android.support.v7.app.NotificationCompat
import com.example.workstation.pdm_se01.R
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.app.PendingIntent
import android.content.*
import android.support.v4.app.TaskStackBuilder
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.workstation.pdm_se01.activities.MainActivity
import com.example.workstation.pdm_se01.components.notification.NotificationReceiver
import com.example.workstation.pdm_se01.model.Weather.Weather
import com.example.workstation.pdm_se01.model.Weather.Wrapper
import com.example.workstation.pdm_se01.utils.Connectivity
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.utils.Utils
import com.example.workstation.pdm_se01.provider.AWAContract
import com.example.workstation.pdm_se01.provider.DbSchema
import java.util.*

class Syncronizer(val context: Context, val contentProvider: ContentResolver){

    val api : API
    val ctx : Context
    init{
        api = API(context)
        ctx = context
    }

    class SyncHandler(content: ContentResolver, _reg: QueryRegist, favorite: Boolean = false){

        val hasRecord : Boolean
        val cr : ContentResolver
        val reg : QueryRegist
        val isFav : Boolean

        init {
            cr = content
            reg = _reg
            hasRecord = hasRecord()
            isFav = favorite
        }

        fun hasRecord() : Boolean {
            var selection = "country = ? AND city = ?"
            val selectionArgs = arrayOf(reg.country, reg.city)
            val cursor = cr.query(AWAContract.Weather.getWeatherList(), null, selection, selectionArgs, null)
            return cursor.moveToFirst();
        }

        public val errHandler = Response.ErrorListener(){
            error ->
            //Toast.makeText(ctx,"Request Failed", Toast.LENGTH_SHORT).show()

        }

        public var repHandler = Response.Listener<String>(){
            response ->
                if(hasRecord()) updateRecord(response)
                else insertNewRecord(response)
        }

        fun insertNewRecord(record:String){
            val cv = ContentValues()
            cv.put(DbSchema.Weather.COL_DATA,record)
            cv.put(DbSchema.Weather.COL_COUNTRY,reg.country)
            cv.put(DbSchema.Weather.COL_CITY,reg.city)
            cv.put(DbSchema.Weather.COL_FAV,isFav)
            cr.insert(AWAContract.CONTENT_URI,cv)
        }

        fun updateRecord(record:String){
            val cv = ContentValues()
            cv.put(DbSchema.Weather.COL_DATA,record)
            cv.put(DbSchema.Weather.COL_FAV,isFav)
            cr.update(AWAContract.CONTENT_URI,cv,"country=? AND city=?" , arrayOf(reg.country,reg.city))
        }
    }

    public fun snycronize(){

        //Obter lista de favoritos
        val favList = arrayOf("UK London","PT Lisbon","FR Paris")
        //Obter connectividade, não sincronizar se falhar na validação
        if(Connectivity.validateConnectivity(context)) return
        //Fazer insert ou update para cada entrada da lista
        favList.forEach {
            val parseData = it.split(' ')
            //index 0 city, index 1 country
            val reg = QueryRegist(parseData[0],parseData[1])
            val syncHandler = SyncHandler(contentProvider,reg)
            api!!.getWeather(reg,syncHandler.repHandler,syncHandler.errHandler)
        }
    }

    public fun sendNotification(title : String, description: String , icon:String){
        val mBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle(title)
                .setContentText(description)
        val resultIntent = Intent(context, NotificationReceiver::class.java)

        val stackBuilder = TaskStackBuilder.create(context)
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity::class.java)
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

/*    public fun addFavorite(city : String, country: String ){
        val syncHandler = SyncHandler(contentProvider,country,city,true)
        api!!.getWeather(country,city,syncHandler.repHandler,syncHandler.errHandler)
    }

    public fun removeFavorite(city : String, country: String ){
        val syncHandler = SyncHandler(contentProvider,country,city,false)
        api!!.getWeather(country,city,syncHandler.repHandler,syncHandler.errHandler)
    }*/
}

