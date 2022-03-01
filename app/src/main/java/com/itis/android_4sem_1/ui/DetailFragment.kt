package com.itis.android_4sem_1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.data.DetailModel
import com.itis.android_4sem_1.api.ApiCreator
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    private var city: String? = null
    private val api = ApiCreator.weatherApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString("CITY_NAME")
        }
        city?.let {
            initWeather(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun initWeather(cityTitle: String) {
        lifecycleScope.launch {
            initWeatherView(api.getWeather(cityTitle))
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initWeatherView(cityWeather: DetailModel) {
        cityName.text = cityWeather.name
        temp.text = context?.resources?.getString(
            R.string.temp,
            cityWeather.main.temp.toInt().toString()
        )
        temp_min.text = context?.resources?.getString(
            R.string.temp_min,
            cityWeather.main.temp.toInt().toString()
        )
        temp_max.text = context?.resources?.getString(
            R.string.temp_max,
            cityWeather.main.temp.toInt().toString()
        )
        feels_like.text = context?.resources?.getString(
            R.string.feels_like,
            cityWeather.main.feelsLike.toInt().toString()
        )
        sunrise.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunrise * 1000)
        sunset.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunset * 1000)
        wind.text = context?.resources?.getString(
            R.string.weather_speed,
            cityWeather.wind.speed.toString()
        )
        pressure.text = cityWeather.main.pressure.toString()
        humidity.text = cityWeather.main.humidity.toString() + "%"
        direction.text =
            when (cityWeather.wind.degree) {
                in 0..22 -> "N"
                in 23..67 -> "N-E"
                in 68..112 -> "E"
                in 113..157 -> "S-E"
                in 158..202 -> "S"
                in 203..247 -> "S-W"
                in 248..292 -> "S"
                in 293..337 -> "N-W"
                in 337..361 -> "N"
                else -> "-"
            }
    }

    companion object {
        fun newInstance(param1: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("CITY_NAME", param1)
                }
            }
    }
}
