package com.kulloveth.cardinfofinder.data

import com.kulloveth.cardinfofinder.model.CardResponse

interface CardRepository {

    suspend fun fetchCardDetails(cardNo: Long): CardResponse
}