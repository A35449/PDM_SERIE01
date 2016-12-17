package com.example.workstation.pdm_se01.components.broadcast

import android.app.*
import android.content.Context
import android.content.Intent
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.utils.Utils
import java.util.*


/**
 * Created by Jos on 14/12/2016.
 */
class UpdateService : IntentService("UpdateService") {

        override fun onHandleIntent(intent: Intent?) {
            Utils.sendNotification(applicationContext,"Update Started","Update Started","")
            //syncNow
        }

        fun syncNow(){
            val sync = Syncronizer(applicationContext,API_Forecast(applicationContext))
            val raw_locals = getSharedPreferences("Location", Context.MODE_PRIVATE).getString("locals",null)
            val locals = raw_locals.split(",".toRegex()).dropLastWhile({it.isEmpty()}).toTypedArray()
            val queries = buildQueries(locals)
            sync.snycronize(queries)
        }

        fun buildQueries(locals : Array<String>) : List<QueryRegist>{
            val retList = ArrayList<QueryRegist>()
            for(l : String in locals){
                val parsed = l.split(",")
                retList.add(QueryRegist(parsed[0],parsed[1],1))
            }
            return retList
        }

    }