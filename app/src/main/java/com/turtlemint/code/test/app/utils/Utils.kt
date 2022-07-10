package com.turtlemint.code.test.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Utils {

    fun getDate(date : String, issueNo : Int) : String{
        var formattedDate :String = ""
        Log.i("getDate", "Updated dt : $date for Issue Number - $issueNo")
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(date)
        Log.i("getDate", "formatter dt : $formatter for Issue Number - $issueNo")
        formattedDate = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(formatter)
        Log.i("getDate", "formattedDate : $formattedDate for Issue Number - $issueNo")
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


