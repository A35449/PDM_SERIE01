package com.example.workstation.pdm_se01.network

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import com.android.volley.Response
import com.example.workstation.pdm_se01.model.Weather.Wrapper

import com.example.workstation.pdm_se01.provider.AWAContract
import com.example.workstation.pdm_se01.utils.Connectivity
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist
import java.util.*

/**
 * Created by workstation on 15/12/2016.
 */

class AWA(_context: Context, _cr : ContentResolver){

    val cr : ContentResolver
    val ctx : Context
    val api : API
    init{
        cr = _cr
        ctx = _context
        api = API(ctx)
    }
    //get record

    // get favorites

    //get search

    //

    public fun getFavorites(query_list :List<QueryRegist>) : ArrayList<Wrapper> {
        val wrappers = ArrayList<Wrapper>()
        for(query in query_list)
        {
            wrappers.add(getRecordOffline(query))
        }
        return wrappers;
    }

    public fun getRecord(regist: QueryRegist){
        if(Connectivity.validateConnectivity(ctx))getRecordOffline(regist)
        //else getRecordOnline(regist)
    }

    public fun getRecordOffline(regist: QueryRegist) : Wrapper {
        var selection = "city = ? AND country = ?"
        val selectionArgs = arrayOf(regist.city, regist.country)
        val cursor = cr.query(AWAContract.Weather.getWeatherList(), null, selection, selectionArgs, null)
        val obj_result = cursor.moveToFirst();
        val json_data = cursor.getString(cursor.getColumnIndex(AWAContract.Weather.DATA))
        return Converter.convertToWeather(json_data)
    }

//    public fun getRecordOnline(regist: QueryRegist, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener) : Wrapper {
//
//    }
}
