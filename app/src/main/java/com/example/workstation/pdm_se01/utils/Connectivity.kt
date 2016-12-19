package com.example.workstation.pdm_se01.utils

import android.content.Context
import android.net.NetworkInfo
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.ConnectivityManager



/**
 * Created by workstation on 15/12/2016.
 */
class Connectivity(){

    companion object{
        val WIFI = 0
        val BOTH = 1

        fun validateConnectivity(context: Context) : Boolean{

            //get connectivity preferences

            val connection_pref = Connectivity.WIFI
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var ret = false
            when(connection_pref){
                WIFI -> ret = checkWIFI(connectivityManager)
                BOTH -> ret = checkBoth(connectivityManager)
            }

            return ret
        }

        private fun checkBoth(connectivityManager : ConnectivityManager): Boolean {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        private fun checkWIFI(connectivityManager: ConnectivityManager):Boolean{
            var networkInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return networkInfoWifi.isConnected
        }
    }




}
