package com.itis.android_4sem_1.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.data.api.WeatherRepositoryImpl
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.di.DIContainer
import com.itis.android_4sem_1.domain.usecases.GetWeatherListUsecase
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import com.itis.android_4sem_1.presentation.viewModel.DetailViewModel
import com.itis.android_4sem_1.utils.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    private var city: String? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initFactory()
        initObservers()
        arguments?.let {
            city = it.getString("CITY_NAME")
        }
        city?.let {
            initWeather(it)
        }
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun initObservers(){
        viewModel.weather.observe(viewLifecycleOwner){ it ->
            it.fold(onSuccess ={
                weatherView(it)
            },onFailure ={
                Log.e("Error","error")
            })
        }
    }

    private fun initFactory(){
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]
    }

    private fun initWeather(cityTitle: String) {
        lifecycleScope.launch {
           viewModel.getWeatherByName(cityTitle)
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun weatherView(cityWeather: DetailModel) {
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
        wind.text = context?.resources?.getString(
            R.string.weather_speed,
            cityWeather.wind.speed.toString()
        )
        pressure.text = context?.resources?.getString(
            R.string.pressure_mm,
            (cityWeather.main.pressure/1.333).toInt().toString()
        )
        humidity.text = cityWeather.main.humidity.toString() + "%"
        sunrise.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunrise * 1000)
        sunset.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunset * 1000)
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
