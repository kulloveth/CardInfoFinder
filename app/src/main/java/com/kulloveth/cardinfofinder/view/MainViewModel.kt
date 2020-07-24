package com.kulloveth.cardinfofinder.view

import androidx.lifecycle.*
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.model.CardResponse
import com.kulloveth.cardinfofinder.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CardRepository) :
    ViewModel() {


    private val _cardDetailsLiveData = MutableLiveData<Resource<CardResponse>>()


    //liveData method to hold response gotten from api
    fun fetchCardDetails(cardNo: Long): LiveData<Resource<CardResponse>> {
        viewModelScope.launch(context = Dispatchers.IO) {
            val details = repository.fetchCardDetails(cardNo)
            _cardDetailsLiveData.postValue(details)
        }
        return _cardDetailsLiveData
    }



}