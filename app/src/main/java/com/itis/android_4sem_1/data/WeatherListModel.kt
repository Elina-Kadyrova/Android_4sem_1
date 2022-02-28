package com.itis.android_4sem_1.data

import com.google.gson.annotations.SerializedName

data class WeatherListModel(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<WeatherDetail>,
    @SerializedName("message")
    val message: String
) {
    data class WeatherDetail (
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: Main,
        @SerializedName("name")
        val name: String,
        @SerializedName("rain")
        val rain: Any,
        @SerializedName("snow")
        val snow: Any,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind")
        val wind: Wind
    ) {
        data class Clouds(
            @SerializedName("all")
            val all: Int
        )

        data class Coord(
            @SerializedName("lat")
            val lat: Double,
            @SerializedName("lon")
            val lon: Double
        )

        data class Main(
            @SerializedName("feels_like")
            val feelsLike: Double,
            @SerializedName("grnd_level")
            val grndLevel: Int,
            @SerializedName("humidity")
            val humidity: Int,
            @SerializedName("pressure")
            val pressure: Int,
            @SerializedName("sea_level")
            val seaLevel: Int,
            @SerializedName("temp")
            val temp: Double,
            @SerializedName("temp_max")
            val tempMax: Double,
            @SerializedName("temp_min")
            val tempMin: Double
        )

        data class Sys(
            @SerializedName("country")
            val country: String
        )

        data class Weather(
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val icon: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("main")
            val main: String
        )

        data class Wind(
            @SerializedName("deg")
            val deg: Int,
            @SerializedName("speed")
            val speed: Double
        )
    }
}
