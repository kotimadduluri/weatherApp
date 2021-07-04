package com.koti.weatherreport.repo

import androidx.lifecycle.LiveData
import com.koti.weatherreport.db.LocationBookMark
import com.koti.weatherreport.db.LocationLastSyncReport
import com.koti.weatherreport.db.LocationWithLastSyncDetails
import com.koti.weatherreport.network.dto.WeatherForecastResponse
import com.koti.weatherreport.network.dto.WeatherTodayResponse
import com.koti.weatherreport.network.NetworkResponse

/**
 * @author koti
 * WeatherRepository skeleton
 */
interface WeatherRepository {

    //location
    fun saveNewLocation(location: LocationBookMark): Long
    fun saveNewLocationReport(report: LocationLastSyncReport): Long
    fun getLocationBookmarks(): LiveData<List<LocationWithLastSyncDetails>>
    fun getLocationBookmarkById(bId:Long): LocationWithLastSyncDetails
    fun deleteLocationBookmarkById(bId:Long)
    fun deleteAllRecords()

    fun getTodayWeatherReport(lat: Double, lon: Double): LiveData<NetworkResponse<WeatherTodayResponse>>
    fun getLocationForecast(lat: Double, lon: Double): LiveData<NetworkResponse<WeatherForecastResponse>>

}