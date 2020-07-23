package com.kulloveth.cardinfofinder.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.data.Repository
import com.kulloveth.cardinfofinder.model.CardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CardRepository = Repository()) :
    ViewModel() {


    private val _cardDetailsLiveData = MutableLiveData<CardResponse>()


    //liveData method to hold data response gotten from api
    fun fetchCardDetails(cardNo: Long): LiveData<CardResponse> {
        viewModelScope.launch(context = Dispatchers.IO) {
            val details = repository.fetchCardDetails(cardNo)
            _cardDetailsLiveData.postValue(details)
        }
        return _cardDetailsLiveData
    }


}