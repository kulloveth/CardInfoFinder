package com.kulloveth.cardinfofinder.model

import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("scheme")
    val scheme: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("country")
    val country: Country,
    @SerializedName("bank")
    val bank: Bank
)