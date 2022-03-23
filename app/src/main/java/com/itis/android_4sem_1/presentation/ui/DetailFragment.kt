package com.itis.android_4sem_1.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.databinding.FragmentDetailBinding
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.di.DIContainer
import com.itis.android_4sem_1.presentation.viewModel.DetailViewModel
import com.itis.android_4sem_1.utils.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private var city: String? = null
    private lateinit var viewModel: DetailViewModel
    private var detailModel: DetailModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        initFactory()
        initObservers()
        binding?.weatherInfo = detailModel
        arguments?.let {
            city = it.getString("CITY_NAME")
        }
        city?.let {
            initWeather(it)
        }
        return binding?.root
    }

    private fun initObservers(){
        viewModel.weather.observe(viewLifecycleOwner){ it ->
            it.fold(
                onSuccess =
                {
                    detailModel = it
                    weatherView(it) },
                onFailure = { Log.e("err","error") })
        }
    }

    private fun initFactory() {
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
        binding?.let {
            cityName.text = cityWeather.name
            temp.text = cityWeather.main.temp.toInt().toString()
            temp_min.text = cityWeather.main.temp.toInt().toString()
            temp_max.text = cityWeather.main.tempMax.toInt().toString()
            feels_like.text = cityWeather.main.feelsLike.toInt().toString()
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
            wind.text = cityWeather.wind.speed.toString()
            pressure.text = (cityWeather.main.pressure / 1.333).toInt().toString()
            humidity.text = cityWeather.main.humidity.toString()
            sunrise.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunrise)
            sunset.text = SimpleDateFormat("HH:mm").format(cityWeather.sys.sunset)
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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
