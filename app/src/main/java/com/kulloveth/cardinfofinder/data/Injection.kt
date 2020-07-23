package com.kulloveth.cardinfofinder.data

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.cardinfofinder.view.ViewModelFactory

object Injection {

    private fun provideRepository(): Repository {
        return Repository()
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository())
    }

}