package com.koti.weatherreport.db

import androidx.room.Embedded
import androidx.room.Relation
data class LocationWithLastSyncDetails(
    @Embedded
    var location: LocationBookMark,
    @Relation(
         parentColumn = "_id",
         entityColumn = "bookmarkId"
     )
     var details: LocationLastSyncReport?
)
