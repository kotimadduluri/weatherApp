package com.koti.weatherreport.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koti.weatherreport.db.LocationLastSyncReport

@Dao
interface LocationLastSyncReportDao {

    @Query("SELECT * FROM locationlastsyncreport where bookmarkId=:bookmarkId")
    fun getAll(bookmarkId:Int): List<LocationLastSyncReport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<LocationLastSyncReport>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LocationLastSyncReport):Long
}