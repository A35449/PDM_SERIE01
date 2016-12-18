package com.example.workstation.pdm_se01.network.api

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import com.example.workstation.pdm_se01.network.SingletonRequest
import com.example.workstation.pdm_se01.network.api.API
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.QueryRegist
import java.util.*

/**
 * Created by workstation on 16/12/2016.
 */

class API_Forecast(context: Context) : API(context) {

    private val FORE_URL = "/data/2.5/forecast/daily"

    init{
        contract = ForecastContract
    }

    companion object {
        private val loader: ImageLoader? = null
    }

    override public fun get(reg: QueryRegist, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener) {
        val ps = String.format(super.BASE_URL + FORE_URL + "?q=%s,%s&appid=%s&lang=%s&units=metric&cnt=7", reg.city, reg.country, API_KEY, lang)
        val req = StringRequest(ps, sucessHandler, errHandler)
        SingletonRequest.getInstance(context).addToRequestQueue(req)
    }
}