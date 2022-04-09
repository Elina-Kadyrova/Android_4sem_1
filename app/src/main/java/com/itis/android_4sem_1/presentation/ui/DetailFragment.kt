package com.itis.android_4sem_1.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.databinding.FragmentDetailBinding
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.presentation.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private val city: String by lazy {
        arguments?.getString("CITY_NAME")?: ""
    }
    @Inject
    lateinit var factory: DetailViewModel.Factory
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(factory, city)
    }
    private var detailModel: DetailModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        initObservers()
        binding?.weatherInfo = detailModel

        initWeather()
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

    private fun initWeather() {
        lifecycleScope.launch {
           viewModel.getWeatherByName()
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun weatherView(cityWeather: DetailModel) {
        binding?.let {
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
