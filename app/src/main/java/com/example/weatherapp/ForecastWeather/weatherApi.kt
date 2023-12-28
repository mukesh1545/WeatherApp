package com.example.weatherapp.ForecastWeather

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("forecast?")
    suspend fun getWeatherApi(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): weatherResponse
}
