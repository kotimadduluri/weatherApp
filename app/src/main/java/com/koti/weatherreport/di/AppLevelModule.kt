package com.koti.weatherreport.di

import android.content.Context
import androidx.room.Room
import com.koti.weatherreport.db.DB_NAME
import com.koti.weatherreport.db.WeatherReportsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author koti
 * Module to provide application level objects
 */
@Module
@InstallIn(SingletonComponent::class)
class AppLevelModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
    context.applicationContext,
    WeatherReportsDataBase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideRepoEntityDao(repoDataBase: WeatherReportsDataBase)=repoDataBase.locationBookMarkDao()

    @Provides
    @Singleton
    fun provideContributorDao(repoDataBase: WeatherReportsDataBase)=repoDataBase.locationLastSyncReportDao()
}