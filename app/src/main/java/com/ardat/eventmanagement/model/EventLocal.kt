package com.ardat.eventmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventLocal")
data class EventLocal(
    @ColumnInfo(name = "eventName") var eventName : String,
    @ColumnInfo(name = "eventDate") var eventDate : String,
    @ColumnInfo(name = "eventInfo")var eventInfo : String,
    @ColumnInfo(name = "eventQuery") var eventQuery : String,
    @ColumnInfo(name = "eventSynced") var eventSynced : String,
    @PrimaryKey @ColumnInfo(name = "eventUid") var eventUid : String
){
    constructor(): this("", "", "", "", "false", "")
}