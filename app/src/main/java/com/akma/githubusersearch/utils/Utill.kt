package com.akma.githubusersearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale


object Utill {
    private var toast: Toast? = null
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


    fun showToast(context: Context?, message: String?) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()

    }
    fun showSnackBar(view: View, message: String?) {
        Snackbar.make(view, message ?: "", Snackbar.LENGTH_LONG).show()
    }

    fun hideToast() {
        if (toast != null) {
            toast!!.cancel()
        }
    }

    fun formatDate(inputDate: String): String {
        // Define the input and output date formats
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        // Parse the input date string
        val date = inputFormat.parse(inputDate)

        // Format to the desired output
        return outputFormat.format(date!!)
    }

}