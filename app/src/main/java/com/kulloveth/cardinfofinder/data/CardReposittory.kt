package com.kulloveth.cardinfofinder.data

import com.kulloveth.cardinfofinder.model.CardResponse
import com.kulloveth.cardinfofinder.network.Resource

interface CardRepository {

    suspend fun fetchCardDetails(cardNo: Long): Resource<CardResponse>
}