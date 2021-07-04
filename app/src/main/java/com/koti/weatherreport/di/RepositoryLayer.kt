package com.koti.weatherreport.di

import android.content.Context
import com.koti.weatherreport.db.LocationBookMarkDao
import com.koti.weatherreport.db.LocationLastSyncReportDao
import com.koti.weatherreport.network.WeatherApi
import com.koti.weatherreport.repo.WeatherRepository
import com.koti.weatherreport.repo.WeatherRepository_Imp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author koti
 * Module to provide application level objects
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryLayer {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        locationBookMarkDao: LocationBookMarkDao,
        locationLastSyncReportDao: LocationLastSyncReportDao
    ):WeatherRepository = WeatherRepository_Imp(weatherApi,locationBookMarkDao, locationLastSyncReportDao)
}