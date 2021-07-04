package com.koti.weatherreport.helper

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

object TimeUtil {
    val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    val timeFormat = SimpleDateFormat("hhaa")
    fun getDateByEpochTimeStamp(time:Int):String=if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        Instant.ofEpochMilli(time*1000L)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toString()
    } else {
        dateFormat.format(Date(time*1000L))
    }

    fun getTimeByEpochTimeStamp(time:Int):String=timeFormat.format(Date(time*1000L))
    fun getLastSyncTime(time:Long):String=dateFormat.format(Date(time*1000L))
}