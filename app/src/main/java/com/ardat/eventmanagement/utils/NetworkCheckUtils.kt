package com.ardat.eventmanagement.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun checkNetworkIsConnected(context: Context) : Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val networks = cm.allNetworks
        var internetConnected = false
        if (networks.isNotEmpty()){
            for (network in networks){
                val nc = cm.getNetworkCapabilities(network)!!
                if(nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    internetConnected = true
                }
            }
        }
        return internetConnected

    } else {
        val networkInfo = cm.activeNetworkInfo!!
        return networkInfo.isConnected
    }
}