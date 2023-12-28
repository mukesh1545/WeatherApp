package com.example.weatherapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveLocation:ViewModel() {

        private val _location = MutableLiveData<String>()
        val location: LiveData<String> get() = _location

        fun updateLocation(newLocation: String) {
            Log.d("mukeshLivedata","$newLocation")
            _location.value = newLocation
            Log.d("mukeshLivedataafter","${_location.value}")
//            Log.d("USER_TEST","${location.value}")
        }
    }

