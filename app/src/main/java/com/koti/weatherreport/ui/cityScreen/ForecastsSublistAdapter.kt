package com.koti.weatherreport.ui.cityScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R
import com.koti.weatherreport.databinding.ItemForecastSublistItemBinding
import com.koti.weatherreport.network.dto.Forecast

class ForecastsSublistAdapter(val items: ArrayList<Forecast>) : RecyclerView.Adapter<ForecastsSublistAdapter.ViewHolder>() {

    inner class ViewHolder(private val binder: ItemForecastSublistItemBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindData(item: Forecast) {
            binder.forecast=item
            binder.executePendingBindings()
            //child.findViewById<ConstraintLayout>(R.id.dataContainer).setBackgroundResource(getCardBackground())
        }

        private fun getCardBackground(): Int {
            return when (adapterPosition%5) {
                0 -> {
                    R.drawable.gradient1
                }
                1 -> {
                    R.drawable.gradient2
                }
                2 -> {
                    R.drawable.gradient3
                }
                3 -> {
                    R.drawable.gradient4
                }
                else -> {
                    R.drawable.gradient5
                }
            }
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


