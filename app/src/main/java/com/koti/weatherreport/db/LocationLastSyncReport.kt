package com.koti.weatherreport.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(entity = LocationBookMark::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("bookmarkId"),
    onDelete = CASCADE)],
    indices = [Index(value = ["bookmarkId"], unique = true)]
)
data class LocationLastSyncReport(
    @PrimaryKey (autoGenerate = true) val _id: Long=0L,
    @ColumnInfo(name = "bookmarkId") val bookmarkId: Long,
    @ColumnInfo(name = "temp") val temp: Double=0.0,
    @ColumnInfo(name = "minTemp") val minTemp: Double=0.0,
    @ColumnInfo(name = "maxTemp") val maxTemp: Double=0.0,
    @ColumnInfo(name = "humidity") val humidity: String,
    @ColumnInfo(name = "windSpeed") val windSpeed:String,
    @ColumnInfo(name = "updatedAt") val updatedAt:Long=(System.currentTimeMillis()/1000)
)
