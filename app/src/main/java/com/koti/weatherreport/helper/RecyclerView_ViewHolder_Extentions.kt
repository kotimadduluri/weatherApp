package com.koti.weatherreport.helper

import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R

fun RecyclerView.ViewHolder.getCardBackground(): Int {
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