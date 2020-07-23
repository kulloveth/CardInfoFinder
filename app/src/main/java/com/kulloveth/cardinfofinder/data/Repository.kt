package com.kulloveth.cardinfofinder.data

import com.kulloveth.cardinfofinder.network.apiService
import com.kulloveth.cardinfofinder.utils.BASE_URL

class Repository : CardRepository {

    override suspend fun fetchCardDetails(cardNo: Long) =
        apiService.getCardDetails(BASE_URL + cardNo)


}