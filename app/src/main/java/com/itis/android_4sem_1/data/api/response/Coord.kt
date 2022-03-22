package com.itis.android_4sem_1.data.api.response

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
