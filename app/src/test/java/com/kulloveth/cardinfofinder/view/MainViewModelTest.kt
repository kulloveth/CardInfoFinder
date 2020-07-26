package com.kulloveth.cardinfofinder.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.model.Bank
import com.kulloveth.cardinfofinder.model.CardResponse
import com.kulloveth.cardinfofinder.model.Country
import com.kulloveth.cardinfofinder.network.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cardRepository: CardRepository
    private lateinit var cardDetailObserver: Observer<Resource<CardResponse>>
    private val validCardNo = 452762543442859732
    private val invalidCardNo = 12345678901
    private val successRessource = Resource.success(
        CardResponse(
            "visa", "", "", Country
                (124, "CA", "Canada", "ca", "CAD", 60, -95),
            Bank("", "", "")
        )
    )
    private val errorResource = Resource.error("Something went wrong", null)


    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        cardRepository = mock()
        runBlocking {
            whenever(cardRepository.fetchCardDetails(validCardNo)).thenReturn(successRessource)
            whenever(cardRepository.fetchCardDetails(invalidCardNo)).thenReturn(errorResource)
        }
        mainViewModel = MainViewModel(cardRepository)
        cardDetailObserver = mock()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when FetchCardDetailIsCalledWithValidCardTheCardDetailIsShown`() = runBlocking {
        mainViewModel.fetchCardDetails(validCardNo).observeForever(cardDetailObserver)
        delay(40)
        verify(cardRepository).fetchCardDetails(validCardNo)
        verify(cardDetailObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(cardDetailObserver, timeout(50)).onChanged(successRessource)
    }

    @Test
    fun `when FetchCardDetailIsCalledWithInValidCardNoErrorMessageIsShown`() = runBlocking {
        mainViewModel.fetchCardDetails(invalidCardNo).observeForever(cardDetailObserver)
        delay(40)
        verify(cardRepository).fetchCardDetails(invalidCardNo)
        verify(cardDetailObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(cardDetailObserver, timeout(50)).onChanged(errorResource)
    }
}