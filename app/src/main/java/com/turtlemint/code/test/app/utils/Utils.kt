package com.turtlemint.code.test.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Utils {

    fun getDate(date : String) : String{
        var formattedDate :String = ""
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(date)
        formattedDate = SimpleDateFormat("MM-dd-yyy", Locale.US).format(formatter)
        return formattedDate;
    }

    fun checkInternetConnectivity(context: Context) : Boolean{
        val cm : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = cm.activeNetwork ?: return false
            val activeNetwork = cm.getNetworkCapabilities(network)
            return when{
                activeNetwork!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->  true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->  true
                else ->  false
            }
        }else{
            @Suppress("DEPRECATION") val networkInfo =
                cm.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }

    }


}


