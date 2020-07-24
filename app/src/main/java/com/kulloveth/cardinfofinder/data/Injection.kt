package com.kulloveth.cardinfofinder.data

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.cardinfofinder.network.ResponseHandler
import com.kulloveth.cardinfofinder.network.RetrofitService
import com.kulloveth.cardinfofinder.network.buildRetrofit
import com.kulloveth.cardinfofinder.view.ViewModelFactory

/**
 * provide [ViewModelFactory]
 * for MainViewModel
 * */
object Injection {

   val retrofitService by lazy { buildRetrofit().create(RetrofitService::class.java) }

    private fun provideRepository(): Repository {
        return Repository(retrofitService, ResponseHandler())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository())
    }

}