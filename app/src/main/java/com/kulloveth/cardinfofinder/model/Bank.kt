package com.kulloveth.cardinfofinder.model

import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("phone") val phone: String
)