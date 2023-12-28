package com.example.weatherapp.ForecastWeather


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherServices{

    private const val BASE_URL="https://api.openweathermap.org/data/2.5/"

    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api= retrofit.create(WeatherApi::class.java)

    suspend fun  getWeather(location:String,apiKey:String) : weatherResponse {
        return api.getWeatherApi(location, apiKey)
    }

}