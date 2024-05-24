package com.danialtavakoli.omdb

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.danialtavakoli.omdb.model.net.NetworkChecker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NetworkCheckerTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var network: Network
    private lateinit var networkCapabilities: NetworkCapabilities

    @Before
    fun setUp() {
        context = Mockito.mock(Context::class.java)
        connectivityManager = Mockito.mock(ConnectivityManager::class.java)
        network = Mockito.mock(Network::class.java)
        networkCapabilities = Mockito.mock(NetworkCapabilities::class.java)

        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
    }

    @Test
    fun testIsInternetConnected_wifi() {
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(network)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)

        val networkChecker = NetworkChecker(context)
        assertTrue(networkChecker.isInternetConnected)
    }

    @Test
    fun testIsInternetConnected_cellular() {
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(network)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            .thenReturn(true)

        val networkChecker = NetworkChecker(context)
        assertTrue(networkChecker.isInternetConnected)
    }

    @Test
    fun testIsInternetConnected_noConnection() {
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(null)

        val networkChecker = NetworkChecker(context)
        assertFalse(networkChecker.isInternetConnected)
    }
}
