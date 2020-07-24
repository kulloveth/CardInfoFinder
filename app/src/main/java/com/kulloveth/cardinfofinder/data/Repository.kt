package com.kulloveth.cardinfofinder.data

import com.kulloveth.cardinfofinder.model.CardResponse
import com.kulloveth.cardinfofinder.network.Resource
import com.kulloveth.cardinfofinder.network.ResponseHandler
import com.kulloveth.cardinfofinder.network.RetrofitService
import com.kulloveth.cardinfofinder.utils.BASE_URL
import retrofit2.HttpException

open class Repository(
    private val retrofitService: RetrofitService,
    private val responseHandler: ResponseHandler
) : CardRepository {
    override suspend fun fetchCardDetails(cardNo: Long): Resource<CardResponse> {
        return try {
            val response = retrofitService.getCardDetails(BASE_URL + cardNo)
            return responseHandler.handleSuccess(response)
        } catch (e: HttpException) {
            responseHandler.handleException(e.code())
        }
    }
}

