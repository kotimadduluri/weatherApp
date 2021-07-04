package com.koti.weatherreport.network

import com.koti.weatherreport.BuildConfig
import com.koti.weatherreport.network.dto.WeatherForecastResponse
import com.koti.weatherreport.network.dto.WeatherTodayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getTodaysReport(
        @Query("appid") appid: String =BuildConfig.REST_API_APPID,
        @Query("lat") lat: Double,
        @Query("lon") lon:Double
    ): WeatherTodayResponse

    @GET("/data/2.5/forecast")
    suspend fun getLocationForecast(
        @Query("appid") appid:String=BuildConfig.REST_API_APPID,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units:String="metric"
    ): WeatherForecastResponse

   // http://api.openweathermap.org/data/2.5/forecast?lat=0&lon=0&appid=fae7190d7e6433ec3a45285ffcf55c86&units=metric
}