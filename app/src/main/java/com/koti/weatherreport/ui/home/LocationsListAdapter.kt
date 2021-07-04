package com.koti.weatherreport.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.koti.weatherreport.R
import com.koti.weatherreport.databinding.ItemWeatherCardsBinding
import com.koti.weatherreport.db.LocationWithLastSyncDetails

class LocationsListAdapter(val itemClickListener: ItemClickListner) : RecyclerView.Adapter<LocationsListAdapter.ViewHolder>() {

    private val locations = ArrayList<LocationWithLastSyncDetails>()

    inner class ViewHolder(private val binder:ItemWeatherCardsBinding) : RecyclerView.ViewHolder(binder.root) {
        lateinit var itemData:LocationWithLastSyncDetails;
        init {
            itemView.findViewById<MaterialCardView>(R.id.itemCard).setOnClickListener {
                itemClickListener.onItemClicked(itemData.location._id)
            }
            itemView.findViewById<ImageButton>(R.id.delete).setOnClickListener {
                itemClickListener.deleteBookmark(itemData.location._id)
            }
        }
        fun bindData(item: LocationWithLastSyncDetails) {
            itemData=item
            itemView.findViewById<ConstraintLayout>(R.id.dataContainer).setBackgroundResource(getCardBackground())
            binder.location=itemData
            binder.executePendingBindings()
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
        return ViewHolder(ItemWeatherCardsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_weather_cards,parent,false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindData(getItem(position))
    }

    override fun getItemCount()=locations.size

    private fun getItem(position: Int)=locations[position]

    fun submitList(it: List<LocationWithLastSyncDetails>?) {
        it?.let {
            locations.clear()
            locations.addAll(it)
            notifyDataSetChanged()
        }
    }

    public interface ItemClickListner{
        fun onItemClicked(location: Long?)
        fun deleteBookmark(location: Long?)
    }
}


