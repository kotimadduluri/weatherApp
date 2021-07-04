package com.koti.weatherreport

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author koti
 * app to hold app level life cycle and provide app level context
 */
@HiltAndroidApp
class WeatherApp : Application() {
}