package com.example.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object util {

    fun updateUI(weather:Double) : Int {

        val currentTempCelsius:Double = weather.minus(273.15)

        //convert it to Fahrenheit
        val TempFahrenheit : Int= (1.8 * currentTempCelsius + 32).toInt()

        return TempFahrenheit

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDateTime: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("MMM-dd", Locale.ENGLISH)

        val dateTime = LocalDateTime.parse(inputDateTime, inputFormatter)
        return outputFormatter.format(dateTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Time(dateTimeString: String): LocalTime {

        val parts = dateTimeString.split(" ")

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val time = LocalTime.parse(parts[1], timeFormatter)
        return time

    }
}