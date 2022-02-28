package com.itis.android_4sem_1.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.data.WeatherListModel

class WeatherAdapter(private var weatherListModel: WeatherListModel,
                     private val onClick:(String) -> (Unit)) :
    RecyclerView.Adapter<WeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather,parent,false),onClick
        )
    }

    override fun getItemCount() = weatherListModel.list.size

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(weatherListModel.list[position])
    }
}
