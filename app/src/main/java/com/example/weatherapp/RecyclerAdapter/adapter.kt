package com.example.weatherapp.RecyclerAdapter

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.EachItemsFragment
import com.example.weatherapp.util
import com.example.weatherapp.ForecastWeather.ForecastItem
import com.example.weatherapp.R
import kotlin.math.absoluteValue
import kotlin.reflect.typeOf

class Adapter (private val weatherList:List<ForecastItem>,private val changer:ChangePosition):RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val Time=itemView.findViewById<TextView>(R.id.Time)
        val max=itemView.findViewById<TextView>(R.id.Max)
        val min=itemView.findViewById<TextView>(R.id.Min)
        val des=itemView.findViewById<TextView>(R.id.description)
        val icon=itemView.findViewById<ImageView>(R.id.icon)
        val Date=itemView.findViewById<TextView>(R.id.Date)
        val layout=itemView.findViewById<LinearLayout>(R.id.Second)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.eachitem, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = weatherList[position]


        holder.layout.setOnClickListener {
            changer.ItemPosition(position)
        }
        if (position == 1 ) {
            holder.Time.setTypeface(null, Typeface.BOLD)
            holder.Date.text=util.formatDate(pos.dt_txt)
            holder.Time.text= " Now "
            holder.des.text = pos.weather[0].description
            holder.min.text = util.updateUI(pos.main.temp_min).absoluteValue.toString()
            holder.max.text = util.updateUI(pos.main.temp_max).absoluteValue.toString()
            val iconUrl = "https://openweathermap.org/img/w/${pos.weather[0].icon}.png"
            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(holder.icon)
        }
        else
        {
            holder.Date.text = util.formatDate(pos.dt_txt)
            holder.Time.setTypeface(null, Typeface.NORMAL)
            holder.Time.text= util.Time(pos.dt_txt).toString()
            holder.des.text = pos.weather[0].description
            holder.min.text = util.updateUI(pos.main.temp_min).absoluteValue.toString()
            holder.max.text = util.updateUI(pos.main.temp_max).absoluteValue.toString()

            val iconUrl = "https://openweathermap.org/img/w/${pos.weather[0].icon}.png"

            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(holder.icon)
        }
    }


    interface ChangePosition
    {
        fun ItemPosition(position:Int)
    }

    }
