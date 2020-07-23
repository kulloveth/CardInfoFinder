package com.kulloveth.cardinfofinder.network

import com.kulloveth.cardinfofinder.model.CardResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {
    @GET
    suspend fun getCardDetails(@Url cardNo: String):CardResponse
}