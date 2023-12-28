package com.example.weatherapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.Activity.MainActivity
import com.example.weatherapp.Activity.location
import com.example.weatherapp.ForecastWeather.WeatherServices
import com.example.weatherapp.RecyclerAdapter.Adapter
import com.example.weatherapp.ViewModel.LiveLocation
import com.example.weatherapp.databinding.FragmentRecyclerViewBinding
import kotlinx.coroutines.launch

class RecyclerViewFragment : Fragment(),MainActivity.ChangeLocation,Adapter.ChangePosition {

    private var _binding: FragmentRecyclerViewBinding? = null


    private lateinit var Adapter: Adapter
    private val binding get() = _binding!!
    private val apiKey = "e774fc1686f8d00d09893d5d9bfd9fc5"
   var prevLocation="salem"
//    private val testLiveData : LiveData<String>
//        get() = sharedViewModel.location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView).visibility = View.VISIBLE
        requireActivity().findViewById<ImageView>(R.id.click).visibility = View.VISIBLE
        refreshWeatherData(prevLocation)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshWeatherData(location: String) {
        // Use lifecycleScope to launch a coroutine for fetching weather data
        lifecycleScope.launch {
            try {
                val weather = WeatherServices.getWeather(location, apiKey)
                Log.d("mukesh_Recycler", location)
                Adapter = Adapter(weather.list, this@RecyclerViewFragment)
                binding.Reclycler.layoutManager = LinearLayoutManager(requireContext())
                binding.Reclycler.adapter = Adapter
                Adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
     ///location....change
    override fun onLocationChanged(location: String) {
         prevLocation= location
         refreshWeatherData(prevLocation)
    }
   /// Position change....!
    override fun ItemPosition(position: Int) {
        // In FragmentA, when you want to navigate to FragmentB
       requireActivity().findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)?.visibility = View.GONE
       requireActivity().findViewById<ImageView>(R.id.click).visibility = View.GONE

       val fragmentB = EachItemsFragment()
        fragmentB.arguments=Bundle().apply {
            putString("key", prevLocation)
            putInt("Position",position)
        }

// Using the supportFragmentManager for AppCompatActivity
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

// Replace the current fragment with FragmentB
        transaction.replace(R.id.fr,fragmentB)

// Optional: Add the transaction to the back stac
        transaction.addToBackStack(null)

// Commit the transaction
        transaction.commit()
    }
}