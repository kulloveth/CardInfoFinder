package com.kulloveth.cardinfofinder.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("numeric") val numeric: Int,
    @SerializedName("alpha2") val alpha2: String,
    @SerializedName("name") val name: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("latitude") val latitude: Int,
    @SerializedName("longitude") val longitude: Int
)