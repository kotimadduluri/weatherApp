package com.koti.weatherreport.ui.cityScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R
import com.koti.weatherreport.databinding.ItemForecastSublistItemBinding
import com.koti.weatherreport.network.dto.Forecast

class ForecastsSublistAdapter(private val items: ArrayList<Forecast>) : RecyclerView.Adapter<ForecastsSublistAdapter.ViewHolder>() {

    inner class ViewHolder(private val binder: ItemForecastSublistItemBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindData(item: Forecast) {
            binder.forecast=item
            binder.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemForecastSublistItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_sublist_item,parent,false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
    override fun getItemCount()=items.size

    private fun getItem(pos:Int)=items[pos]
}


