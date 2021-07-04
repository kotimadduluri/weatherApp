package com.koti.weatherreport.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koti.weatherreport.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    fun getLocationBookmarks()=weatherRepository.getLocationBookmarks()

    fun deleteLocationBookmarkById(bId:Long)=weatherRepository.deleteLocationBookmarkById(bId)
}