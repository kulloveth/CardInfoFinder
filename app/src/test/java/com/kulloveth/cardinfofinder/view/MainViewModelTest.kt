package com.kulloveth.cardinfofinder.view

import androidx.lifecycle.Observer
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.model.CardResponse
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest{
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cardRepository: CardRepository
    private lateinit var cardDetailObserver:Observer<CardResponse>

}