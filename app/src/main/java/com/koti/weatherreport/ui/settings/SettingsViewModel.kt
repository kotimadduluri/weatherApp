package com.koti.weatherreport.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koti.weatherreport.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {
    fun deleteAllLocation()=weatherRepository.deleteAllRecords()
}