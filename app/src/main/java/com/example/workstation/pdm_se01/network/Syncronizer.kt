package com.example.workstation.pdm_se01.network

import android.support.v7.app.NotificationCompat
import com.example.workstation.pdm_se01.R
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.app.PendingIntent
import android.content.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v4.app.TaskStackBuilder
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.workstation.pdm_se01.activities.MainActivity
import com.example.workstation.pdm_se01.activities.WeatherActivity
import com.example.workstation.pdm_se01.activities.WeatherByLocation
import com.example.workstation.pdm_se01.components.notification.NotificationReceiver
import com.example.workstation.pdm_se01.model.Weather.Weather
import com.example.workstation.pdm_se01.model.Weather.Wrapper
import com.example.workstation.pdm_se01.network.api.API
import com.example.workstation.pdm_se01.utils.Connectivity
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.utils.Utils
import com.example.workstation.pdm_se01.provider.contract.AWAContract
import com.example.workstation.pdm_se01.provider.DbSchema
import java.util.*

class Syncronizer(val context: Context, _api : API){

    val api : API
    val ctx : Context
    val contract: AWAContract
    init{
        api = _api
        ctx = context
        contract = _api.contract!!
    }

    internal class SyncHandler(ctx: Context,  _contract : AWAContract, _reg: QueryRegist , favorite: Boolean = false){

        val cr : ContentResolver
        val reg : QueryRegist
        val isFav : Boolean
        val contract : AWAContract

        init {
            cr = ctx.contentResolver
            reg = _reg
            isFav = favorite
            contract = _contract
        }

        /*Handlers*/
        val errHandler = Response.ErrorListener(){
            error ->
            Toast.makeText(ctx,"Network request failed to complete.", Toast.LENGTH_SHORT).show()
        }

        val syncHandler = Response.Listener<String>(){
            response ->
            if(hasRecord()) updateRecord(response)
            else insertNewRecord(response)
            //lança activity
            val locationIntent = Intent(ctx,WeatherByLocation::class.java)
            ctx.startActivity(locationIntent)
        }

        val syncToActivityHandler = Response.Listener<String>(){
            response ->
            if(hasRecord()) updateRecord(response)
            else insertNewRecord(response)
            //lança activity
            val locationIntent = Intent(ctx,WeatherByLocation::class.java)
            locationIntent.putExtra("location",reg.city+ "," + reg.country)
            locationIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(locationIntent)
        }

        /*Functions*/
        fun hasRecord() : Boolean {
            var selection = "country = ? AND city = ?"
            val selectionArgs = arrayOf(reg.country, reg.city)
            val cursor = cr.query(contract.getAll(), null, selection, selectionArgs, null)
            return cursor.moveToFirst();
        }

        fun insertNewRecord(record:String){
            val cv = ContentValues()
            cv.put(contract.DATA,record)
            cv.put(contract.COUNTRY,reg.country)
            cv.put(contract.CITY,reg.city)
            cv.put(contract.FAV,isFav)
            cr.insert(contract.CONTENT_URI,cv)
        }

        fun updateRecord(record:String){
            val cv = ContentValues()
            cv.put(contract.DATA,record)
            cv.put(contract.FAV,isFav)
            cr.update(contract.CONTENT_URI,cv,"country=? AND city=?" , arrayOf(reg.country,reg.city))
        }
    }

    public fun snycronize(favList : List<QueryRegist>){

        //Obter connectividade, não sincronizar se falhar na validação
        if(!Connectivity.validateConnectivity(context)) return
        //Fazer insert ou update para cada entrada da lista
        favList.forEach {
            syncronizeSingle(it)
        }
    }

    public fun syncronizeSingle(reg : QueryRegist){
        val sync = SyncHandler(context,contract,reg)
        api!!.get(reg,sync.syncHandler,sync.errHandler)
    }

    public fun syncronizeSearch(reg:QueryRegist){
        val sync = SyncHandler(context,contract,reg)
        api!!.get(reg,sync.syncToActivityHandler,sync.errHandler)
    }

    /*  public fun addFavorite(city : String, country: String ){
        val syncHandler = SyncHandler(contentProvider,country,city,true)
        api!!.getWeather(country,city,syncHandler.repHandler,syncHandler.errHandler)
    }

    public fun removeFavorite(city : String, country: String ){
        val syncHandler = SyncHandler(contentProvider,country,city,false)
        api!!.getWeather(country,city,syncHandler.repHandler,syncHandler.errHandler)
    }*/
}

