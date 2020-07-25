package com.kulloveth.cardinfofinder

import android.app.Application
import com.facebook.stetho.Stetho

class CardApp : Application() {

    companion object {
        private lateinit var instance: CardApp
        fun getContext() = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        /* GlobalScope.launch(context = Dispatchers.IO) {
            apiService.getCardDetails(BASE_URL+)
         }*/

        Stetho.initializeWithDefaults(this)
    }
}