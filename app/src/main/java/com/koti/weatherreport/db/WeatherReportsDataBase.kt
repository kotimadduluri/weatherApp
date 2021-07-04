package com.koti.weatherreport.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author koti
 * database to capture data
 */
const val DB_NAME="_cache_wedr"
const val DB_VERSION=1
@Database(entities = [LocationBookMark::class,LocationLastSyncReport::class],version = DB_VERSION,exportSchema = false)
abstract class WeatherReportsDataBase :  RoomDatabase(){
    abstract fun locationBookMarkDao():LocationBookMarkDao
    abstract fun locationLastSyncReportDao():LocationLastSyncReportDao
}