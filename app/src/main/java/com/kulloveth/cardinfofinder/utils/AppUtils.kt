package com.kulloveth.cardinfofinder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

//covert string to editable
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

//compare nos
fun Int?.isLessThan(other: Int?) =
    this != null && other != null && this < other

//check that string chars are numbers only
fun String.intOrString() = toIntOrNull() ?: this

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
//show snackbar
fun View.showSnackBar(message: String) {
    val params: ConstraintLayout.LayoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    params.setMargins(50, 50, 20, 50)
    this.layoutParams = params
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
}

//check network connection
fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}