/**
 * NetworkChecker is a utility class used to check the network connectivity status.
 * It provides a method to determine whether the device is connected to the internet.
 *
 * @property context the context used to access system services.
 */

package com.danialtavakoli.omdb.model.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class NetworkChecker(private val context: Context) {

    /**
     * Checks whether the device is connected to the internet.
     *
     * @return true if the device is connected to the internet, false otherwise.
     */
    val isInternetConnected: Boolean
        get() {
            val result: Boolean
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val myNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
        }
}


/**
 * Extension function to display a toast message.
 *
 * @param message the message to be displayed in the toast.
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
