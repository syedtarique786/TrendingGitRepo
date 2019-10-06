/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData

/**
 * A LiveData class which wraps the network connection status
 * Requires Permission: ACCESS_NETWORK_STATE
 **/
class ConnectionLiveData @VisibleForTesting internal constructor(
    private val connectivityManager: ConnectivityManager
) : MutableLiveData<Boolean>() {

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    constructor(application: Application) : this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    private lateinit var networkCallback: NetworkCallback

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = NetworkCallback(this)
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                networkCallback
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val builder = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }


    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallback(private val liveData: ConnectionLiveData) :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            liveData.postValue(true)
        }

        override fun onLost(network: Network?) {
            liveData.postValue(false)
        }
    }
}