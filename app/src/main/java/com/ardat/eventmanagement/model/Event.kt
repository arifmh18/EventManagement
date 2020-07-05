package com.ardat.eventmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event(
    @ColumnInfo(name = "eventName") var eventName : String,
    @ColumnInfo(name = "eventDate") var eventDate : String,
    @ColumnInfo(name = "eventInfo")var eventInfo : String,
    @PrimaryKey @ColumnInfo(name = "eventUid") var eventUid : String
){
    constructor(): this("", "", "", "")
}