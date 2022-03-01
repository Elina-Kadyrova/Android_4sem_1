package com.itis.android_4sem_1.rv

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_4sem_1.R
import com.itis.android_4sem_1.data.DetailModel

class WeatherHolder(
    itemView: View,
    private val onClick: (String) -> Unit)
    : RecyclerView.ViewHolder(itemView)
{
    private val city: TextView = itemView.findViewById(R.id.item_name)
    private val temp: TextView = itemView.findViewById(R.id.item_temp)

    fun bind(weatherDetail: DetailModel) {
        city.text = weatherDetail.name
        val temperature = weatherDetail.main.temp.toInt()

        when (temperature){
            in 30..100 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperature_20))
            in 15..30 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperature_10))
            in 0..15 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperature_0))
            in -15..0 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperatureCold_10))
            in -30..-15 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperatureCold_20))
            in -100..-30 -> temp.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorTemperatureCold_100))
        }

        temp.text = temperature.toString()

        itemView.setOnClickListener {
            onClick(weatherDetail.name)
        }
    }
}
