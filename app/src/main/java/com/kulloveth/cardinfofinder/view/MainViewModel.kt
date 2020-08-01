package com.kulloveth.cardinfofinder.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.model.CardResponse
import com.kulloveth.cardinfofinder.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: CardRepository) :
    ViewModel() {

    //liveData method to hold response gotten from api
    fun fetchCardDetails(cardNo: Long) =
        liveData {
            emit(repository.fetchCardDetails(cardNo))
            emit(Resource.loading(null))
    }

}