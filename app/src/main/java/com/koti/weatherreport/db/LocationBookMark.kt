package com.koti.weatherreport.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationBookMark(
    @PrimaryKey (autoGenerate = true) var _id: Long=0L,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "created") val created: Long=(System.currentTimeMillis()/1000)
)
