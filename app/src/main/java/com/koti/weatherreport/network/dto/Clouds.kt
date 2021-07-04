package com.koti.weatherreport.network.dto


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int
)