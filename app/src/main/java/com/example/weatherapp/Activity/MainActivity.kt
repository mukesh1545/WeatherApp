package com.example.weatherapp.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherapp.ForecastWeather.WeatherServices
import com.example.weatherapp.ForecastWeather.weatherResponse
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.LiveLocation
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

var location="Erode"
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private var LocationModel=LiveLocation()
    val apiKey = "e774fc1686f8d00d09893d5d9bfd9fc5"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWeatherData()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle text submission
                location = query.toString()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change
                location = newText.toString()
                return true
            }
        })

        binding.click.setOnClickListener {
            notifyFragment(location)
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            getWeatherData()
        }

    }

    private fun notifyFragment(location: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fr)
        if (fragment is ChangeLocation) {
            fragment.onLocationChanged(location)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getWeatherData() {

        lifecycleScope.launch {
            try {
                val weather = WeatherServices.getWeather(location, apiKey)
                updateUI(weather)
                binding.searchView.setQuery("", false)
                binding.searchView.onActionViewCollapsed()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weather: weatherResponse) {
        val currentTempCelsius = weather.list.get(1).main.temp.minus(273.15)
        val maxTempCelsius = weather.list.get(1).main.temp_max.minus(273.15)
        val minTempCelsius = weather.list.get(1).main.temp_min.minus(273.15)

        //convert it to Fahrenheit
        val currentTempFahrenheit = 1.8 * currentTempCelsius + 32
        val maxTempFahrenheit = 1.8 * maxTempCelsius + 32
        val minTempFahrenheit = 1.8 * minTempCelsius + 32

        //Log.d("mukesh", "${currentTempFahrenheit} ${maxTempFahrenheit} ${minTempFahrenheit}")

        //Displaying the Values
        binding.CurrentTemp.text = currentTempFahrenheit.toInt().toString()

        binding.Humidty.text = " Humidity: ${weather.list.get(1).main.humidity}"
        binding.MaxTemp.text = maxTempFahrenheit.toInt().toString()
        binding.MinTemp.text = minTempFahrenheit.toInt().toString()
        binding.NameCity.text = "${weather.city.name}, ${weather.city.country}"
        binding.Descrpition.text = weather.list.get(1).weather.get(0).description
        val iconUrl ="https://openweathermap.org/img/w/${weather.list.get(1).weather.get(0).icon}.png"
        // Use a library like Glide or Picasso to load the image into an ImageView
        // Example using Glide:

        Glide.with(this)
            .load(iconUrl)
            .into(binding.image)
    }
    interface ChangeLocation {
        fun onLocationChanged(location: String)
    }
}

