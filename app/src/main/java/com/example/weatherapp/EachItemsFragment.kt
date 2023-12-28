package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Activity.MainActivity
import com.example.weatherapp.ForecastWeather.WeatherServices
import com.example.weatherapp.ForecastWeather.weatherResponse
import com.example.weatherapp.RecyclerAdapter.Adapter
import com.example.weatherapp.databinding.FragmentEachItemsBinding
import kotlinx.coroutines.launch

class EachItemsFragment : Fragment(){
    private var _binding: FragmentEachItemsBinding? = null
    private val binding get() = _binding!!
    private val apiKey = "e774fc1686f8d00d09893d5d9bfd9fc5"
    private var receivedData: String? = null
    private var position: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEachItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedData = arguments?.getString("key")
        position = arguments?.getInt("Position")
        refreshWeatherData(receivedData!!)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun refreshWeatherData(location: String) {
        // Use lifecycleScope to launch a coroutine for fetching weather data
        lifecycleScope.launch {
            try {
                val weather = WeatherServices.getWeather(location, apiKey)
                Log.d("mukesh_Recycler","$weather")
                updateUI(weather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun updateUI(weather: weatherResponse) {
        val maxTempCelsius = weather.list.get(position!!).main.temp_max.minus(273.15)
        val minTempCelsius = weather.list.get(position!!).main.temp_min.minus(273.15)

        //convert it to Fahrenheit
        val maxTempFahrenheit = 1.8 * maxTempCelsius + 32
        val minTempFahrenheit = 1.8 * minTempCelsius + 32

        //Log.d("mukesh", "${currentTempFahrenheit} ${maxTempFahrenheit} ${minTempFahrenheit}")
        if (position == 1) {
            binding.timeEachitem.text = "NOW"
            binding.dateEachItem.text = util.formatDate(weather.list.get(position!!).dt_txt)
            //binding.timeEachitem.text = util.Time(weather.list.get(position!!).dt_txt).toString()
            binding.descriptionEachItems.text =
                "Description : ${weather.list.get(position!!).weather.get(0).description}"
            binding.EachHumidity.text = " Humidity: ${weather.list.get(position!!).main.humidity}"
            binding.maxEachItems.text = "Max : ${maxTempFahrenheit.toInt()}"
            binding.minEachItem.text = "Min : ${minTempFahrenheit.toInt()}"
            binding.wSpeed.text = "Wind Direction :${weather.list.get(position!!).wind.speed}"
            binding.wDirection.text = "Wind Speed :${weather.list.get(position!!).wind.deg}"
            binding.Pressure.text = "Pressure: ${weather.list.get(position!!).main.pressure}"
            binding.seaLevel.text = "Sea Level: ${weather.list.get(position!!).main.sea_level}"
            val iconUrl =
                "https://openweathermap.org/img/w/${weather.list.get(position!!).weather.get(0).icon}.png"
            // Use a library like Glide or Picasso to load the image into an ImageView
            // Example using Glide:

            Glide.with(this)
                .load(iconUrl)
                .into(binding.ImageEachItem)
        } else {

            binding.dateEachItem.text = util.formatDate(weather.list.get(position!!).dt_txt)
            binding.timeEachitem.text = util.Time(weather.list.get(position!!).dt_txt).toString()
            binding.descriptionEachItems.text =
                "Description : ${weather.list.get(position!!).weather.get(0).description}"
            binding.EachHumidity.text = " Humidity: ${weather.list.get(position!!).main.humidity}"
            binding.maxEachItems.text = "Max : ${maxTempFahrenheit.toInt()}"
            binding.minEachItem.text = "Min : ${minTempFahrenheit.toInt()}"
            binding.wSpeed.text = "Wind Direction :${weather.list.get(position!!).wind.speed}"
            binding.wDirection.text = "Wind Speed :${weather.list.get(position!!).wind.deg}"
            binding.Pressure.text = "Pressure: ${weather.list.get(position!!).main.pressure}"
            binding.seaLevel.text = "Sea Level: ${weather.list.get(position!!).main.sea_level}"
            val iconUrl =
                "https://openweathermap.org/img/w/${weather.list.get(position!!).weather.get(0).icon}.png"
            // Use a library like Glide or Picasso to load the image into an ImageView
            // Example using Glide:

            Glide.with(this)
                .load(iconUrl)
                .into(binding.ImageEachItem)
        }
    }
}
