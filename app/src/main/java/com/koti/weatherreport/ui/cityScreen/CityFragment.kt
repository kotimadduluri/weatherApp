package com.koti.weatherreport.ui.cityScreen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R
import com.koti.weatherreport.db.LocationLastSyncReport
import com.koti.weatherreport.db.LocationWithLastSyncDetails
import com.koti.weatherreport.helper.DataConverter
import com.koti.weatherreport.helper.TimeUtil.getDateByEpochTimeStamp
import com.koti.weatherreport.network.dto.Forecast
import com.koti.weatherreport.network.dto.WeatherForecastResponse
import com.koti.weatherreport.network.dto.WeatherTodayResponse
import com.koti.weatherreport.network.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@AndroidEntryPoint
class CityFragment : Fragment(R.layout.city_fragment) {

    companion object {
        fun newInstance() = CityFragment()
    }
    private val viewModel by viewModels<CityViewModel>()

    private lateinit var _tvLocationName:TextView
    private lateinit var _tvTemp:TextView
    private lateinit var _tvTempMin:TextView
    private lateinit var _tvTempMax:TextView
    private lateinit var _tvWeather:TextView
    private lateinit var _tvHumidity:TextView
    private lateinit var _tvWind:TextView
    private lateinit var _rvForecasts:RecyclerView
    private lateinit var _tvForecastLabel:TextView

    private val forecastListAdapter=ForecastsListAdapter()

    val args: CityFragmentArgs by navArgs()
    val locationId: Long by lazy {
        args.cityId
    }
    val selectedLocation:LocationWithLastSyncDetails by lazy {
        viewModel.getLocationDetailsById(locationId)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getLocationData()
    }

    private fun getLocationData() {
        _tvLocationName.text=selectedLocation.location.name
        observeTodayForecast()
        observeLocationForecast()
    }

    private fun observeLocationForecast() {
        viewModel.getLocationForecast(selectedLocation.location.lat,selectedLocation.location.lon).observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkResponse.STATUS.INPROGRESS -> {
                    _tvForecastLabel.text = "Checking forecasts...."
                }
                NetworkResponse.STATUS.SUCCESS -> {
                    it?.data?.let {response->
                        processForecastData(response)
                        _tvForecastLabel.text = "Daily Forecasts"
                    }
                }
                NetworkResponse.STATUS.ERROR -> {
                    _tvForecastLabel.text = "Error ! Tap to retry"
                    _tvForecastLabel.setOnClickListener {
                        getLocationData()
                        _tvForecastLabel.setOnClickListener(null)
                    }
                    Toast.makeText(requireContext(), "Fail to get data", Toast.LENGTH_LONG).show()
                }
                else -> {
                    //we can add required logic
                }
            }
        })
    }

    private fun processForecastData(data: WeatherForecastResponse) {
        try{
            val filteredList=HashMap<String,ArrayList<Forecast>>()
            data.list.forEach {forecast->
                val date=getDateByEpochTimeStamp(forecast.dt)
                if(filteredList.containsKey(date)){
                    filteredList[date]?.add(forecast)
                }else{
                    filteredList[date] = arrayListOf(forecast)
                }
            }
            forecastListAdapter.submitData(filteredList)
           // println("Date===>$filteredList")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        view?.let {
            _tvLocationName=it.findViewById(R.id.locationName)
            _tvTemp=it.findViewById(R.id.temp)
            _tvTempMax=it.findViewById(R.id.temp_max)
            _tvTempMin=it.findViewById(R.id.temp_min)
            _tvWeather=it.findViewById(R.id.weatherType)
            _tvWind=it.findViewById(R.id.wind)
            _tvHumidity=it.findViewById(R.id.humidity)
            _tvForecastLabel=it.findViewById(R.id.forecast)
            _rvForecasts=it.findViewById<RecyclerView>(R.id.forecastList).apply {
                adapter=forecastListAdapter
            }
        }
    }

    private fun observeTodayForecast() {
        viewModel.getTodayWeather(selectedLocation.location.lat,selectedLocation.location.lon).observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkResponse.STATUS.INPROGRESS -> {
                    //Toast.makeText(requireContext(), "Fetching details", Toast.LENGTH_LONG).show()
                }
                NetworkResponse.STATUS.SUCCESS -> {
                    bindData(it.data)
                }
                NetworkResponse.STATUS.ERROR -> {
                    Toast.makeText(requireContext(), "Fail to get data", Toast.LENGTH_LONG).show()
                }
                else -> {
                    //we can add required logic
                }
            }
        })
    }

    private fun bindData(data: WeatherTodayResponse?) {
        _tvTemp.text="${DataConverter.getRoundedTemp(data?.main?.feelsLike)}${0x00B0.toChar()}C"
        _tvTempMax.text="${DataConverter.getRoundedTemp(data?.main?.tempMax)}${0x00B0.toChar()}C"
        _tvTempMin.text="${DataConverter.getRoundedTemp(data?.main?.tempMin)}${0x00B0.toChar()}C/"
        _tvWeather.text="${data?.weather?.get(0)?.main}"
        _tvWind.text="${data?.wind?.speed}"
        _tvHumidity.text="${data?.main?.humidity}"

        val report=LocationLastSyncReport(
            bookmarkId = locationId,
            temp = data?.main?.feelsLike!!,
            minTemp = data.main.tempMin,
            maxTemp = data.main.tempMax,
            windSpeed = "${data.wind.speed}",
            humidity = "${data.main.humidity}",
        )

        //saving last report
        viewModel.saveLocationDataReport(report)

    }

}