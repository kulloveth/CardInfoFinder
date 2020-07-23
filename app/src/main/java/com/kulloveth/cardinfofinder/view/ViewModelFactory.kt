package com.kulloveth.cardinfofinder.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.cardinfofinder.data.Repository

/**
 * factory class to construct
 * the [MainViewModel] with its
 * @param repository
 * */
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}