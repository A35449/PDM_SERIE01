package com.example.workstation.pdm_se01.network

import android.content.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import com.android.volley.Response
import com.example.workstation.pdm_se01.activities.WeatherByLocation
import com.example.workstation.pdm_se01.network.api.API
import com.example.workstation.pdm_se01.utils.Connectivity
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.provider.contract.AWAContract
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
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

    internal class SyncHandler(ctx: Context,  _contract : AWAContract, _reg: QueryRegist){

        val cr : ContentResolver
        val reg : QueryRegist
        val contract : AWAContract

        init {
            cr = ctx.contentResolver
            reg = _reg
            contract = _contract
        }

        /*Handlers*/
        val errHandler = Response.ErrorListener {
            error ->
            Toast.makeText(ctx,"Network request failed to complete.", Toast.LENGTH_SHORT).show()
        }

        val syncHandler = Response.Listener<String> {
            response ->
            if(hasRecord())
                updateRecord(response)
            else
                insertNewRecord(response)
        }

        val syncToActivityHandler = Response.Listener<String> {
            response ->
            if(hasRecord())
                updateRecord(response)
            else
                insertNewRecord(response)
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
            return cursor.moveToFirst()
        }

        fun insertNewRecord(record:String){
            val cv = ContentValues()
            cv.put(contract.getData(),record)
            cv.put(contract.getFav(),reg.fav)
            cv.put(contract.getCity(),reg.city)
            cv.put(contract.getCountry(),reg.country)
            cr.insert(contract.CONTENT_URI,cv)
        }

        fun updateRecord(record:String){
            val cv = ContentValues()
            cv.put(contract.getData(),record)
            cv.put(contract.getFav(),reg.fav)
            cr.update(contract.CONTENT_URI,cv,"country=? AND city=?" , arrayOf(reg.country,reg.city))
        }
    }

    fun snycronize(favList : List<QueryRegist>){

        //Obter connectividade, não sincronizar se falhar na validação
        if(!Connectivity.validateConnectivity(context)) return
        //Fazer insert ou update para cada entrada da lista
        favList.forEach {
            syncronizeSingle(it)
        }
    }

    fun syncronizeSingle(reg : QueryRegist){
        val sync = SyncHandler(context,contract,reg)
        api.get(reg,sync.syncHandler,sync.errHandler)
    }

    fun syncronizeSearch(reg:QueryRegist){
        val sync = SyncHandler(context,contract,reg)
        api.get(reg,sync.syncToActivityHandler,sync.errHandler)
    }

    fun updateRecord(reg:QueryRegist ) {
        val cv = ContentValues()
        cv.put(contract.getFav(),reg.fav)
        context.contentResolver.update(contract.CONTENT_URI,cv,"country=? AND city=?" , arrayOf(reg.country,reg.city))
    }
    fun getFav(reg:QueryRegist) : Int{
        var selection = "country = ? AND city = ? "
        val selectionArgs = arrayOf(reg.country, reg.city)
        val cursor = context.contentResolver.query(contract.getAll(), null, selection, selectionArgs, null)
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(ForecastContract.FAV))
        }
        return 0
    }
    fun toggleFav(reg: QueryRegist) {
        var selection = "country = ? AND city = ? and fav=1"
        val selectionArgs = arrayOf(reg.country, reg.city)
        val cursor = context.contentResolver.query(contract.getAll(), null, selection, selectionArgs, null)
        if(!cursor.moveToFirst()){
            reg.fav=1
        }
        else reg.fav=0
        updateRecord(reg)
    }

    fun removeFav(reg : QueryRegist) : Int{
        var selection = "country = ? AND city = ? "
        val selectionArgs = arrayOf(reg.country, reg.city)
        return context.contentResolver.delete(contract.getAll(),selection,selectionArgs)
    }

    fun getFavorites() : List<QueryRegist>{
        var list = ArrayList<QueryRegist>()
        var selection = "fav = ?"
        val selectionArgs = arrayOf("1")
        val cursor = context.contentResolver.query(contract.getAll(), null, selection, selectionArgs, null)
        while(cursor.moveToNext()){

            val city = cursor.getString(cursor.getColumnIndex(contract.getCity()))
            val country = cursor.getString(cursor.getColumnIndex(contract.getCountry()))
            list.add(QueryRegist(city,country,1))
        }

        return list
    }
}

