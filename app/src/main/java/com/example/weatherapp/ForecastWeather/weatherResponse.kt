package com.example.weatherapp.ForecastWeather

import com.google.gson.annotations.SerializedName

data class weatherResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val sys: Sys,
    val wind:Wind,
    val dt_txt: String
)
data class Wind(
    val speed:Double,
    val deg :Double,
    val gust:Double
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min") val temp_min: Double,
    @SerializedName("temp_max") val temp_max: Double,
    val humidity: Double,
    val pressure: Double,
    val sea_level: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Sys(
    val pod: String
)

data class City(
    val name: String,
    val country: String
)

