package com.koti.weatherreport.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocationBookMarkDao {

    @Query("SELECT * FROM locationbookmark")
    fun getAll(): List<LocationBookMark>

    @Transaction
    @Query("SELECT * FROM locationbookmark")
    fun getAllWithReports(): LiveData<List<LocationWithLastSyncDetails>>

    @Transaction
    @Query("SELECT * FROM locationbookmark where _id=:bookmarkId")
    fun getWithReportById(bookmarkId:Long): LocationWithLastSyncDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<LocationBookMark>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LocationBookMark):Long

    @Query("Delete from LocationBookMark")
    fun deleteAll()

    @Query("Delete from LocationBookMark where _id=:bookmarkId")
    fun deleteById(bookmarkId:Long)
}