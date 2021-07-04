package com.koti.weatherreport.ui.cityScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R
import com.koti.weatherreport.databinding.ItemForecastCardsBinding
import com.koti.weatherreport.helper.getCardBackground
import com.koti.weatherreport.network.dto.Forecast
import com.koti.weatherreport.pojo.ForecastListItem

class ForecastsListAdapter() : RecyclerView.Adapter<ForecastsListAdapter.ViewHolder>() {
        private val filteredList= HashMap<String, ArrayList<Forecast>>()

    inner class ViewHolder(private val binder: ItemForecastCardsBinding) : RecyclerView.ViewHolder(binder.root) {
        init {
        }
        fun bindData(item: ForecastListItem) {
            with(binder.root){
                binder.forecast=item
                binder.executePendingBindings()
                findViewById<ConstraintLayout>(R.id.dataContainer).setBackgroundResource(getCardBackground())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemForecastCardsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_cards,parent,false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindData(getItem(position))
    }

    override fun getItemCount()=filteredList.size

    private fun getItem(pos:Int): ForecastListItem {
        val data= filteredList.toList()[pos]
        return ForecastListItem(
            data.first,data.second
        )
    }

    fun submitData(filteredList: HashMap<String, ArrayList<Forecast>>) {
        this.filteredList.clear()
        this.filteredList.putAll(filteredList)
        notifyDataSetChanged()
    }

}


