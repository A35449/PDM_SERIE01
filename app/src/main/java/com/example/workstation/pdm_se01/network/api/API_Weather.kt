package com.example.workstation.pdm_se01.network.api

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.StringRequest
import com.example.workstation.pdm_se01.network.SingletonRequest
import com.example.workstation.pdm_se01.network.api.API
import com.example.workstation.pdm_se01.utils.QueryRegist

class API_Weather(context: Context) : API(context) {

    private val WEA_URL = "/data/2.5/weather"

    override public fun get(reg: QueryRegist, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener) {
        val ps = String.format(super.BASE_URL + WEA_URL +  "?q=%s,%s&appid=%s&lang=%s", reg.city, reg.country, API_KEY, lang)
        val req = StringRequest(ps, sucessHandler, errHandler)
        SingletonRequest.getInstance(context).addToRequestQueue(req)
    }
}
/**
 * Created by workstation on 16/12/2016.
 */
