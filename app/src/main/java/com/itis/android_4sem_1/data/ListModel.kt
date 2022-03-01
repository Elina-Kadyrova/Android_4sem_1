package com.itis.android_4sem_1.data

import com.google.gson.annotations.SerializedName

data class ListModel(
    @SerializedName("list")
    val list: List<DetailModel>,
)
