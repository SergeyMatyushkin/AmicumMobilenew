package com.example.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.config.Const

class Network(
    private val context: Context
) {
    /**
     * Метод получения состояния сети и определения типа запроса
     */
    fun getTypeRequest(): String {
        var result = "false"
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return Const.LOCAL_REQUEST_METHOD
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return Const.LOCAL_REQUEST_METHOD
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Const.SERVER_REMOTE_REQUEST_METHOD
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Const.SERVER_REMOTE_REQUEST_METHOD
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> Const.SERVER_REMOTE_REQUEST_METHOD
                else -> Const.LOCAL_REQUEST_METHOD
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> Const.SERVER_REMOTE_REQUEST_METHOD
                        ConnectivityManager.TYPE_MOBILE -> Const.SERVER_REMOTE_REQUEST_METHOD
                        ConnectivityManager.TYPE_ETHERNET -> Const.SERVER_REMOTE_REQUEST_METHOD
                        else -> Const.LOCAL_REQUEST_METHOD
                    }

                }
            }
        }

        return result
    }
}