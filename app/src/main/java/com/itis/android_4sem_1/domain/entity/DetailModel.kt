package com.itis.android_4sem_1.domain.entity

import com.itis.android_4sem_1.data.api.response.*

data class DetailModel(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val lastupdate: Lastupdate,
    val main: Main,
    val name: String,
    val rain: Rain,
    val precipiration: Precipiration,
    val snow: Snow,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
)
