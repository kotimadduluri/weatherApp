package com.koti.weatherreport.repo

import androidx.lifecycle.liveData
import com.koti.weatherreport.db.*
import com.koti.weatherreport.network.dto.WeatherForecastResponse
import com.koti.weatherreport.network.dto.WeatherTodayResponse
import com.koti.weatherreport.network.NetworkResponse
import com.koti.weatherreport.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * repo to provide data to view
 * @param weatherApi [WeatherApi] interface to get apis
 */
class WeatherRepository_Imp
@Inject constructor(
    private val weatherApi: WeatherApi,
    private val locationBookMarkDao: LocationBookMarkDao,
    private val locationLastSyncReportDao: LocationLastSyncReportDao
) : WeatherRepository {

    override fun saveNewLocation(location:LocationBookMark)
    =locationBookMarkDao.insert(location)

    override fun saveNewLocationReport(report: LocationLastSyncReport)
    =locationLastSyncReportDao.insert(report)

    override fun getLocationBookmarks()=locationBookMarkDao.getAllWithReports()

    override fun getLocationBookmarkById(bId:Long)=locationBookMarkDao.getWithReportById(bId)

    override fun deleteLocationBookmarkById(bId: Long)=locationBookMarkDao.deleteById(bId)

    override fun deleteAllRecords()=locationBookMarkDao.deleteAll()

    override fun getTodayWeatherReport(lat: Double, lon: Double)= liveData<NetworkResponse<WeatherTodayResponse>>(Dispatchers.IO) {
        emit(NetworkResponse.Loading())
        try{
            emit(NetworkResponse.Success(weatherApi.getTodaysReport(lat =lat, lon = lon)))
        }catch (e:Exception){
            emit(NetworkResponse.Error())
            e.printStackTrace()
        }
    }

    override fun getLocationForecast(lat: Double, lon: Double)= liveData<NetworkResponse<WeatherForecastResponse>>(Dispatchers.IO) {
        emit(NetworkResponse.Loading())
        try{
            emit(NetworkResponse.Success(weatherApi.getLocationForecast(lat =lat, lon = lon)))
        }catch (e:Exception){
            emit(NetworkResponse.Error())
            e.printStackTrace()
        }
    }
}