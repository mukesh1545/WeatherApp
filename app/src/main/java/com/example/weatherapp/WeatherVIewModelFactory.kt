package com.example.weatherapp
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ViewModel.LiveLocation

class WeatherVIewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveLocation::class.java)) {
            return  LiveLocation() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}