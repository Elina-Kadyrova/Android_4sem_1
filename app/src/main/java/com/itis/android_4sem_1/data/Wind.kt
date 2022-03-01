package com.itis.android_4sem_1.data

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("degree")
    val degree: Int,
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("gust")
    val gust: Double,
)
