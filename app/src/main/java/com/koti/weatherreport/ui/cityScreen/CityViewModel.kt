package com.koti.weatherreport.ui.cityScreen

import androidx.lifecycle.ViewModel
import com.koti.weatherreport.db.LocationLastSyncReport
import com.koti.weatherreport.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    fun getLocationDetailsById(bId:Long)=weatherRepository.getLocationBookmarkById(bId)

    fun saveLocationDataReport(report:LocationLastSyncReport)=weatherRepository.saveNewLocationReport(report)

    fun getTodayWeather(lat: Double, lon: Double) = weatherRepository.getTodayWeatherReport(lat,lon)

    fun getLocationForecast(lat: Double, lon: Double) = weatherRepository.getLocationForecast(lat,lon)
}