package com.koti.weatherreport.network.dto


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.koti.weatherreport.R
import com.koti.weatherreport.db.LocationLastSyncReport
import com.koti.weatherreport.helper.DataConverter
import com.koti.weatherreport.helper.TimeUtil

@BindingAdapter("setForecastIcon")
fun attachForecastDetails(view: ImageView, type:String){
    view.setImageResource(
        when {
            type.equals("Clouds",false) -> {
                R.drawable.ic_rainy
            }
            type.equals("Rain",false) -> {
                R.drawable.ic_sun
            }
            else -> {
                R.drawable.ic_cloud
            }
        }
    )
}

@BindingAdapter("setTime")
fun setForecastTime(view: TextView, time:Int){
    view.text = TimeUtil.getTimeByEpochTimeStamp(time)
}

@BindingAdapter("setLastSyncTime")
fun setLastSyncTime(view: TextView, details:LocationLastSyncReport?){
    details?.let {
        view.text = "Last sync : ${TimeUtil.getLastSyncTime(details?.updatedAt)}"
    }?:{
        view.text=""
    }
}

@BindingAdapter("setTemperature")
fun setTemperature(view: TextView, time:Double){
    view.text = "${time.toInt()}${0x00B0.toChar()}C"
}

@BindingAdapter("setTemperature")
fun setTemperature(view: TextView, report:LocationLastSyncReport?){
    report?.let {
        view.text = "${DataConverter.getRoundedTemp(it.temp)}${0x00B0.toChar()}C"
    }?:{
        view.text="0"
    }
}

@BindingAdapter("setHumidity")
fun setHumidity(view: TextView, report:LocationLastSyncReport?){
    report?.let {
        view.text = "${report.humidity}"
    }?:{
        view.text="0"
    }
}

@BindingAdapter("setWind")
fun setWind(view: TextView, report:LocationLastSyncReport?){
    report?.let {
        view.text = "${report.windSpeed}"
    }?:{
        view.text="0"
    }
}

data class Forecast(
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("dt_txt")
    var dtTxt: String,
    @SerializedName("main")
    var main: Main,
    @SerializedName("pop")
    var pop: Double,
    @SerializedName("rain")
    var rain: Rain,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind
)