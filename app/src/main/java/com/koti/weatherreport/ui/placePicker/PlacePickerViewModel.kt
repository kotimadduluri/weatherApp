package com.koti.weatherreport.ui.placePicker

import androidx.lifecycle.ViewModel
import com.koti.weatherreport.db.LocationBookMark
import com.koti.weatherreport.repo.WeatherRepository
import com.koti.weatherreport.repo.WeatherRepository_Imp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class PlacePickerViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    fun saveLocation(location: LocationBookMark) = weatherRepository.saveNewLocation(location)
}