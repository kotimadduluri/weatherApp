package com.koti.weatherreport.helper

import kotlin.math.roundToInt

object DataConverter {
    fun getRoundedTemp(temp: Double?)= (temp?.times(0.10))?.roundToInt()
}
