package com.koti.weatherreport.network.dto


import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("city")
    var city: City,
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("cod")
    var cod: String,
    @SerializedName("list")
    var list: List<Forecast>,
    @SerializedName("message")
    var message: Int
)