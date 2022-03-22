package com.itis.android_4sem_1.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.data.api.WeatherRepositoryImpl
import com.itis.android_4sem_1.di.DIContainer
import com.itis.android_4sem_1.domain.usecases.GetWeatherListUsecase
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import com.itis.android_4sem_1.presentation.rv.WeatherAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchCityFragment : Fragment()  {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null
    private val repository = WeatherRepositoryImpl(DIContainer.weatherApi)
    private var getWeatherListUseCase = GetWeatherListUsecase(repository = repository)
    private var getWeatherUseCase = GetWeatherUsecase(repository = repository)
    private val DEFAULT_LATITUDE = 54.7887
    private val DEFAULT_LONGITUDE = 49.1221

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.also {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_list)
        searchView = view.findViewById(R.id.searchView)
        getLastLocation()
        searchCity()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        checkPermissions()
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                    location->
                if (location != null) {
                    initRv(location.latitude, location.longitude)
                }
                else{
                    Snackbar.make(requireView(), "Your location is not available", Snackbar.LENGTH_SHORT).show()
                    initRv(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                }
            }
    }

    private fun checkPermissions(){
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ){
            initRv(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
        }
    }

    private fun initRv(latitude:Double, longitude:Double){
        recyclerView?.run{
            lifecycleScope.launch {
                adapter = WeatherAdapter(
                   getWeatherListUseCase(longitude, latitude)
                ) { cityName ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container,
                            DetailFragment.newInstance(cityName)
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun searchCity(){
        searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    try {
                        getWeatherUseCase(query)
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.container,
                                DetailFragment.newInstance(query)
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                    catch (exception:Exception){
                        Snackbar.make(requireView(), "Couldn't find the city", Snackbar.LENGTH_SHORT).show()
                    }
                }
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    companion object {
        fun newInstance() = SearchCityFragment()
    }
}
