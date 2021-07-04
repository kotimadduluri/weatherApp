package com.koti.weatherreport.pojo

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.network.dto.Forecast
import com.koti.weatherreport.ui.cityScreen.ForecastsSublistAdapter

@BindingAdapter("attachForecastDetails")
fun attachForecastDetails(recyclerView: RecyclerView, list: ArrayList<Forecast>){
    recyclerView.adapter= ForecastsSublistAdapter(list)
}
data class ForecastListItem(
    val date:String,
    val forecasts:ArrayList<Forecast>
)
